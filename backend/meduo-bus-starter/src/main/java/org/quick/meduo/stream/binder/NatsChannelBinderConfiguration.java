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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import io.nats.client.ConnectionListener;
import io.nats.client.ErrorListener;
import org.quick.meduo.nats.autoconfigure.NatsAutoConfiguration;
import org.quick.meduo.nats.autoconfigure.NatsProperties;
import org.quick.meduo.stream.binder.properties.NatsBinderConfigurationProperties;
import org.quick.meduo.stream.binder.properties.NatsExtendedBindingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.config.BindingHandlerAdvise.MappingsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
@Import({ NatsAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class })
@EnableConfigurationProperties({NatsExtendedBindingProperties.class, NatsBinderConfigurationProperties.class})
/**
 * NatsChannelBinderConfiguration is used to parametrize a new NATS binder. This configuration provides custom error and connection listeners.
 */
public class NatsChannelBinderConfiguration {

	@Autowired(required = false)
	/**
	 * A custom connection listener, otherwise a simple logging default is used.
	 */
	private ConnectionListener connectionListener;

	@Autowired(required = false)
	/**
	 * A custom error listener, otherwise a simple logging default is used.
	 */
	private ErrorListener errorListener;

	@Autowired
	/**
	 * The NatsProperties configured to define this binders NATS connections. These are configured globally.
	 */
	private NatsProperties natsProperties;

	@Autowired
	/**
	 * Local configuration properties, with the same options as NatsProperties, but configured specifically for a binder.
	 */
	private NatsBinderConfigurationProperties natsBinderConfigurationProperties;

	@Autowired
	/**
	 * Extended binding properties, unused currently.
	 */
	private NatsExtendedBindingProperties natsExtendedBindingProperties;

	/**
	 * @return custom properties for this binding configuration
	 */
	public NatsBinderConfigurationProperties getNatsBinderConfigurationProperties() {
		return this.natsBinderConfigurationProperties;
	}

	/**
	 * @param natsBinderConfigurationProperties custom properties for this configuration
	 */
	public void setNatsBinderConfigurationProperties(NatsBinderConfigurationProperties natsBinderConfigurationProperties) {
		this.natsBinderConfigurationProperties = natsBinderConfigurationProperties;
	}

	public NatsExtendedBindingProperties getNatsExtendedBindingProperties() {
		return this.natsExtendedBindingProperties;
	}

	public void setNatsExtendedBindingProperties(NatsExtendedBindingProperties natsExtendedBindingProperties) {
		this.natsExtendedBindingProperties = natsExtendedBindingProperties;
	}

	/**
	 * @return global NATS connection properties associated with this binder configuration
	 */
	public NatsProperties getNatsProperties() {
		return this.natsProperties;
	}


	/**
	 * @param natsProperties global NATS connection properties associated with this binder configuration
	 */
	public void setNatsProperties(NatsProperties natsProperties) {
		this.natsProperties = natsProperties;
	}

	@Bean
	/**
	 * @return provisioner for NATS channels
	 */
	public NatsChannelProvisioner natsChannelProvisioner() {
		return new NatsChannelProvisioner();
	}

	@Bean
	/**
	 * @return binder, based on the channel provisioner, using the properties associated with this configuration
	 */
	public NatsChannelBinder natsBinder(NatsChannelProvisioner natsProvisioner) throws IOException, InterruptedException {
		NatsChannelBinder binder = new NatsChannelBinder(this.natsExtendedBindingProperties,
										this.natsBinderConfigurationProperties,
										this.natsProperties, natsProvisioner,
										this.connectionListener,
										this.errorListener);
		return binder.getConnection() != null ? binder : null;
	}

	@Bean
	@StreamMessageConverter
	public MappingFastJsonMessageConverter mappingFastJsonMessageConverter(){
		return new MappingFastJsonMessageConverter();
	}

//	@Bean
//	@StreamMessageConverter
//	public CompositeMessageConverter compositeMessageConverter(){
//		Collection<MessageConverter> converters = new CopyOnWriteArrayList<>();
//		converters.add(mappingFastJsonMessageConverter());
//		CompositeMessageConverter converter = new CompositeMessageConverter(converters);
//		return converter;
//	}


//	@Bean
//	/**
//	 * @return mapping between nats.spring.cloud.stream and nats.spring.cloud.stream
//	 */
//	public MappingsProvider natsExtendedPropertiesDefaultMappingsProvider() {
//		return () -> Collections.singletonMap(
//				ConfigurationPropertyName.of("spring.cloud.stream.nats"),
//				ConfigurationPropertyName.of("spring.cloud.stream.nats"));
//	}
}
