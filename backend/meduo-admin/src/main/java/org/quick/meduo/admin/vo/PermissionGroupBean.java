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

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import org.quick.meduo.common.constants.PermissionGrantType;

import java.util.Date;

@Getter
@Setter
public class PermissionGroupBean {
    private String name;
    private String description;
    private PermissionGrantType type;
    private int expirationDate;
    private String serviceId;
}
