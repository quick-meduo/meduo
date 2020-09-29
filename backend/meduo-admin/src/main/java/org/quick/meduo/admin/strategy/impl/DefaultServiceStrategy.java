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

package org.quick.meduo.admin.strategy.impl;

import org.quick.meduo.admin.model.ServiceModel;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.strategy.IServiceExecStrategy;
import org.quick.meduo.core.strategy.IExecStrategy;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/7
  *@Since: 1.0
  */
public class DefaultServiceStrategy implements IServiceExecStrategy {
    @Override
    public boolean preHandle(String method, Object[] args) {
        boolean result = false;
        switch (method) {
            case "update":
                ServiceModel model = (ServiceModel) args[0];

                break;
        }
        return true;
    }

    @Override
    public boolean postHandle(String method, Object[] args) {
        return true;
    }
}
