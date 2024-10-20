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

package org.quick.meduo.datasource.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

 /**
  *@Description:
  * Mybatis Configuration for Mybatis plus. Currently only MySql supported
  *@TODO:
  * 1' Will support oracle when needed
  *@Changelog:
  *@Authors: Brian.G @2019/11/12
  *@Since: 1.0
  */

@Configuration
@ConditionalOnClass(DataSource.class)
public class MeduoDataConfig {
    @Autowired
    private DataSource dataSource;

     @Bean
     public PaginationInterceptor paginationInterceptor() {
         return new PaginationInterceptor();
     }

     /**
      * Enable ptimisticLocker. please add a field annatated by @Version
      * for example:
      * @Version
      * private Integer version;
      * please also check mybatis plus offical document.
      * @return OptimisticLockerInterceptor
      */
     @Bean
     public OptimisticLockerInterceptor optimisticLockerInterceptor() {
         return new OptimisticLockerInterceptor();
     }
}
