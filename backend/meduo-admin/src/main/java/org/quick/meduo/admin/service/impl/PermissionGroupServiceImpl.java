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

package org.quick.meduo.admin.service.impl;

import org.quick.meduo.admin.mapper.PermissionGroupMapper;
import org.quick.meduo.admin.model.PermissionGroupModel;
import org.quick.meduo.admin.service.PermissionGroupService;
import org.quick.meduo.core.service.impl.MeduoServiceImpl;
import org.springframework.stereotype.Service;

/**
  *@Description:
  * Role Service implementation
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/14
  *@Since: 1.0
  */
@Service
public class PermissionGroupServiceImpl extends MeduoServiceImpl<PermissionGroupMapper, PermissionGroupModel> implements PermissionGroupService {
}
