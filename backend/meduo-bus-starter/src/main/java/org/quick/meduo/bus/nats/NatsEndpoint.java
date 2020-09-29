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

package org.quick.meduo.bus.nats;

import io.nats.client.Connection;
import org.quick.meduo.bus.api.Subscriber;
import org.quick.meduo.bus.config.NatsSubscriberAnnotationBeanPostProcessor;
import org.quick.meduo.nats.autoconfigure.NatsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Endpoint(id = "nats")
public class NatsEndpoint {
    @Autowired
    private NatsProperties properties;
    @Autowired
    private Connection nats;
    @Autowired
    private NatsSubscriberAnnotationBeanPostProcessor postProcessor;

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> info = new HashMap<>();
        info.put("status", nats.getStatus().toString());
        info.put("url", properties.getServer());
        info.put("subjects", postProcessor.getSubscriptions().keySet().stream().map(Subscriber::subject).collect(Collectors.toList()));
        info.put("statistics", nats.getStatistics());
        return info;
    }
}
