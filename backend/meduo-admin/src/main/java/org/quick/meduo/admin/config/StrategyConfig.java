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

package org.quick.meduo.admin.config;

import org.quick.meduo.admin.strategy.impl.DefaultPermissionStrategy;
import org.quick.meduo.admin.strategy.impl.DefaultServiceStrategy;
import org.quick.meduo.admin.strategy.impl.DefaultUserStrategy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class StrategyConfig {

    @Bean
    @ConditionalOnMissingBean()
    public DefaultUserStrategy defaultUserStrategy(){
        return new DefaultUserStrategy();
    }

    @Bean
    @ConditionalOnMissingBean()
    public DefaultServiceStrategy defaultServiceStrategy(){
        return new DefaultServiceStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultPermissionStrategy defaultPermissionStrategy() {
        return new DefaultPermissionStrategy();
    }
}
