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

package org.quick.meduo.bus.config;

import io.nats.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class NatsHealthIndicator extends AbstractHealthIndicator {
    @Autowired
    private Connection nats;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            Connection.Status status = nats.getStatus();
            builder.up().withDetail("url", nats.getConnectedUrl()).withDetail("status", status.toString());
        } catch (Exception e) {
            builder.down(e);
        }
    }
}
