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

package org.quick.meduo.admin.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/10
  *@Since: 1.0
  */
@Setter
public class FeatureGroupBean {
    @NotEmpty
    private String serviceId;
    private String groupName;
    private String groupDesc;
    private String featureName;
    private String featureDesc;
    private String securityCode;

    public String getFeatureDesc() {
        return featureDesc != null ? featureDesc.trim() : featureDesc;
    }

    public String getFeatureName() {
        return featureName != null ? featureName.trim() : featureName;
    }

    public String getGroupDesc() {
        return groupDesc != null ? groupDesc.trim() : groupDesc;
    }

    public String getGroupName() {
        return groupName != null ? groupName.trim() : groupName;
    }

    public String getSecurityCode() {
        return securityCode != null ? securityCode.trim() : securityCode;
    }

    public String getServiceId() {
        return serviceId != null ? serviceId.trim() : serviceId;
    }
}
