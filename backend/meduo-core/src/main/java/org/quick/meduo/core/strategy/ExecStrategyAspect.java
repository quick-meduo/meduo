/*
 * Copyright (c) 2020 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *     https://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.quick.meduo.core.strategy;

import org.aopalliance.aop.Advice;
import org.quick.meduo.core.annotations.ExecStrategy;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/7
  *@Since: 1.0
  */
@Component
public class ExecStrategyAspect extends AbstractPointcutAdvisor {
     @Autowired
     private ExecInteceptor interceptor;

     private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
         @Override
         public boolean matches(Method method, Class<?> targetClass) {
             return method.isAnnotationPresent(ExecStrategy.class) || targetClass.isAnnotationPresent(ExecStrategy.class);
         }
     };

     @Override
     public Pointcut getPointcut() {
         return this.pointcut;
     }

     @Override
     public Advice getAdvice() {
         return this.interceptor;
     }
 }
