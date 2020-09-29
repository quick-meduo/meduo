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

package org.quick.meduo.core.config;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MeduoContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     * @return ApplicationContext对象
     * @throws
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param name String类型
     * @return ApplicationContext对象
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        Object obj = null;
        try {
            obj = applicationContext.getBean(name);
            if (obj == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return (T) obj;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param requiredType Class<T>类型
     * @return T 对象
     * @throws
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        T obj = null;
        try {
            obj = applicationContext.getBean(requiredType);
            if (obj == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    /**
     * 清除Holder
     * @return void
     * @throws
     */
    public static void clearHolder() {
        applicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     * @param  applicationContext ApplicationContext类型
     * @return void
     * @throws
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        MeduoContextHolder.applicationContext = applicationContext;

        if(MeduoContextHolder.applicationContext!=null){
            try {
                ConfigurableApplicationContext context = (ConfigurableApplicationContext) MeduoContextHolder.applicationContext;
                if(context!=null){
                    context.registerShutdownHook();
                }
            }catch (Throwable e){}

        }
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     * @return void
     * @throws
     */
    @Override
    public void destroy() throws Exception {
        MeduoContextHolder.clearHolder();
    }

    /**
     * 检查ApplicationContext不为空.
     * @return void
     * @throws
     */
    private static void assertContextInjected() {
        Validate.validState(applicationContext != null, "请注入应用上下文");
    }
}