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

package org.quick.meduo.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.model.PermissionGroupModel;
import org.quick.meduo.common.constants.PermissionGrantType;
import org.quick.meduo.tools.core.date.DateField;
import org.quick.meduo.tools.core.date.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/14
  *@Since: 1.0
  */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testPermissionGroupService {
    @Autowired
    PermissionGroupService roleService;

    @Test
    public void TestRoleAdd(){
        PermissionGroupModel r = new PermissionGroupModel();
        r.setName("Admin");
        r.setDescription("管理角色");
        r.setType(PermissionGrantType.PERMANENT);
        r.setExpirationDate(DateTime.now().offset(DateField.MONTH,3));
        r.setFrozen(false);
        r.setDelFlag(1);

        roleService.saveOrUpdate(r);
    }
}
