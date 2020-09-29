/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.admin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.quick.meduo.admin.utils.SecurityUtils;
import org.quick.meduo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MybatisMetaOperatorObjectHandler implements MetaObjectHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisMetaOperatorObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        String user = SecurityUtils.getUsername();
        if(StringUtils.isBlank(user)){
            user = "__meduo__internal__";
        }

        this.setInsertFieldValByName("createDate", new Date(), metaObject);
        this.setInsertFieldValByName("createUser", user, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String user = SecurityUtils.getUsername();
        if(StringUtils.isBlank(user)){
            user = "__meduo__internal__";
        }

        this.setUpdateFieldValByName("updateDate", new Date(), metaObject);
        this.setInsertFieldValByName("updateUser", user, metaObject);
    }
}