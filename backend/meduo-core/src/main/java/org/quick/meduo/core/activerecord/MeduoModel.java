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

package org.quick.meduo.core.activerecord;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

 /**
  *@Description:
  * Data Entity base
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/15
  *@Since: 1.0
  */
public abstract class MeduoModel<T extends com.baomidou.mybatisplus.extension.activerecord.Model<?>> extends Model<T> implements Serializable{
}
