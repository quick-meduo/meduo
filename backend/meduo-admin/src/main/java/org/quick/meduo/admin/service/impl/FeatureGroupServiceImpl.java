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

package org.quick.meduo.admin.service.impl;

import org.quick.meduo.admin.mapper.FeatureGroupMapper;
import org.quick.meduo.admin.model.FeatureGroupModel;
import org.quick.meduo.admin.service.FeatureGroupService;
import org.quick.meduo.admin.vo.FeatureGroupBean;
import org.quick.meduo.core.http.Page;
import org.quick.meduo.core.service.impl.MeduoServiceImpl;
import org.springframework.stereotype.Service;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/10
  *@Since: 1.0
  */
@Service
public class FeatureGroupServiceImpl extends MeduoServiceImpl<FeatureGroupMapper, FeatureGroupModel> implements FeatureGroupService {
    @Override
    public Page listFeatureGroup(Page page, FeatureGroupBean query) {
       return this.baseMapper.selectFeatureGroupWrapper(page,query);
    }
}

