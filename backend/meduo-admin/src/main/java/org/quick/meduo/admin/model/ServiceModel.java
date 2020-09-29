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

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.quick.meduo.core.activerecord.MeduoModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName(value = "sys_service",resultMap = "BaseResultMap")
public class ServiceModel extends MeduoModel<ServiceModel> implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String sid;

    private String name;

    private String avatar;

    @TableField(exist = false)
    private List<FeatureGroupModel> featureGroupModels;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date   updateDate;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
