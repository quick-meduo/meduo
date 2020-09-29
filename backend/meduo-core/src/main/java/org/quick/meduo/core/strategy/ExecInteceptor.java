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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.quick.meduo.core.annotations.ExecStrategy;
import org.quick.meduo.core.config.MeduoContextHolder;
import org.quick.meduo.core.constant.ApiConstants;
import org.quick.meduo.core.http.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/7
  *@Since: 1.0
  */
@Component
public class ExecInteceptor implements MethodInterceptor {
    private static Logger log= LoggerFactory.getLogger(ExecInteceptor.class);
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        ExecStrategy anno = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), ExecStrategy.class);
        IExecStrategy strategy = MeduoContextHolder.getBean(anno.value());
        Object retVal = null;

        if(strategy!=null && strategy.preHandle(methodInvocation.getMethod().getName(),methodInvocation.getArguments())){
            retVal = methodInvocation.proceed();
            if(strategy.postHandle(methodInvocation.getMethod().getName(),methodInvocation.getArguments())){
                return retVal;
            }
        } else if (strategy==null){
            log.warn("{}策略类尚未注册",anno.value());
            return Result.ok(ApiConstants.FAILD_TO_FIND_STRATEGY);
        }
        return Result.ok(ApiConstants.FAILD_TO_VERIFY_STRATEGY);
    }
}
