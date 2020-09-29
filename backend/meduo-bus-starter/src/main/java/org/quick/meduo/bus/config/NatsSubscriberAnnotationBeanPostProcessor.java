/*
 *
 *  * Copyright (c) 2019.  the original author or authors.
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.quick.meduo.bus.config;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.quick.meduo.bus.api.*;
import org.quick.meduo.bus.helper.JSonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class NatsSubscriberAnnotationBeanPostProcessor implements Reactive, BeanPostProcessor, Ordered, BeanFactoryAware, InitializingBean, DisposableBean {
    private Logger log = LoggerFactory.getLogger(NatsSubscriberAnnotationBeanPostProcessor.class);
    private BeanFactory beanFactory;
    private Map<Subscriber, Dispatcher> subscriptions = new HashMap<>();
    private Connection nats;

    @Override
    public void afterPropertiesSet() throws Exception {
        nats = beanFactory.getBean(Connection.class);
    }

    public Map<Subscriber, Dispatcher> getSubscriptions() {
        return subscriptions;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, Set<Subscriber>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<Set<Subscriber>>) method -> {
                    Set<Subscriber> listenerMethods = findSubscriberAnnotations(method);
                    return (!listenerMethods.isEmpty() ? listenerMethods : null);
                });
        // Non-empty set of subscribed methods
        if (!annotatedMethods.isEmpty()) {
            try {
                for (Map.Entry<Method, Set<Subscriber>> entry : annotatedMethods.entrySet()) {
                    Method method = entry.getKey();
                    for (Subscriber listener : entry.getValue()) {
                        processNatsSubscriberReflect(listener, method, bean, beanName);
                    }
                }
            } catch (Exception e) {
                throw new BeanCreationException("Failed to construct NATS Subscriber handler", e);
            }
        }



        return bean;
    }

    private Set<Subscriber> findSubscriberAnnotations(Method method) {
        Set<Subscriber> subscribers = new HashSet<>();
        Subscriber natsSubscriber = AnnotationUtils.findAnnotation(method, Subscriber.class);
        if (natsSubscriber != null) {
            subscribers.add(natsSubscriber);
        }
        Subscribers natsSubscribers = AnnotationUtils.findAnnotation(method, Subscribers.class);
        if (natsSubscribers != null) {
            subscribers.addAll(Arrays.asList(natsSubscribers.value()));
        }
        return subscribers;
    }

    private void processNatsSubscriberReflect(Subscriber natsSubscriber, Method method, Object bean, String beanName) throws NoSuchMethodException, IllegalAccessException {
        //use method handler instead of reflection for performance
        MessageHandler messageHandler = msg -> {
            try {
                if (method.getParameterTypes().length != 1) {
                    throw new IllegalArgumentException(
                            "@Initialized should be annotated on only-one argument method");
                }
                ReflectionUtils.makeAccessible(method);
                ReflectionUtils.invokeMethod(method, bean,JSonHelper.parseObject(msg.getData(),method.getParameterTypes()[0]));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
        Dispatcher dispatcher;
        if ("".equals(natsSubscriber.queueGroup())) {
            dispatcher = nats.createDispatcher(messageHandler);
            dispatcher.subscribe(natsSubscriber.subject());
        } else {
            dispatcher = nats.createDispatcher(messageHandler);
            dispatcher.subscribe(natsSubscriber.subject(), natsSubscriber.queueGroup());
        }
        subscriptions.put(natsSubscriber, dispatcher);
    }

    @Override
    public Mono<Void> publish(String subject, byte[] body) {
        CompletableFuture<Message> request = nats.request(subject, body);
        return Mono.fromFuture(request).then();
    }

    @Override
    public Flux<byte[]> subscribe(String subject) {
        return Flux.create(sink -> {
            Dispatcher dispatcher = nats.createDispatcher(message -> {
                sink.next(message.getData());
            });
            subscriptions.put(createAnnotation(subject, null), dispatcher);
        });
    }

    @Override
    public void destroy() throws Exception {
        for (Map.Entry<Subscriber, Dispatcher> entry : subscriptions.entrySet()) {
            Dispatcher dispatcher = entry.getValue();
            Subscriber natsSubscriber = entry.getKey();
            try {
                dispatcher.unsubscribe(natsSubscriber.subject());
                nats.closeDispatcher(dispatcher);
            } catch (Exception e) {
                log.error("Failed to close " + natsSubscriber.subject(), e);
            }
        }
    }

    private MethodHandle getMethodHandler(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(void.class, Message.class);
        return lookup.findVirtual(method.getDeclaringClass(), method.getName(), mt);
    }

    public Subscriber createAnnotation(String subject, String queueGroup) {
        return new Subscriber() {
            @Override
            public String subject() {
                return null;
            }

            @Override
            public String queueGroup() {
                return null;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Subscriber.class;
            }
        };
    }
}
