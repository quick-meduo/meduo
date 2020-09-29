/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.stream.binder;

import org.springframework.cloud.stream.provisioning.ProducerDestination;

/**
 * NATS uses subjects for sending and receiving. While partitions are not used
 * this class can generate a patition-based name for compatibility with the binder
 * API.
 */
public class NatsProducerDestination implements ProducerDestination {
	private String name;

	public NatsProducerDestination(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getNameForPartition(int partition) {
		return this.name + "-" + partition;
	}
}
