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

package org.quick.meduo.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.quick.meduo.core.activerecord.MeduoModel;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *@Description:
 *@TODO:
 *@Changelog:
 *@Authors: Brian.G @2020/06/12
 *@Since: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName(value = "sys_feature")
public class FeatureModel extends MeduoModel<ServiceModel> implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @NotEmpty
    private String gid;

    private String name;

    private String code;

    private String description;
}
