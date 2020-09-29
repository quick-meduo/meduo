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

import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.strategy.IUserExecStrategy;
import org.quick.meduo.admin.utils.UserUtils;

public class DefaultUserStrategy implements IUserExecStrategy {
    private boolean lockCheck(UserModel operator, String id) {
        // 不能冻结自己
        if(operator.getId().equals(id)){
            return false;
        }
        return true;
    }

    @Override
    public boolean preHandle(String method, Object[] args) {
        boolean result = false;
        switch (method) {
            case "lock":
                UserModel currentUser = UserUtils.currentUser();
                String id = (String) args[0];
                return lockCheck(currentUser,id);
        }
        return true;
    }

    @Override
    public boolean postHandle(String method, Object[] args) {
        return true;
    }
}
