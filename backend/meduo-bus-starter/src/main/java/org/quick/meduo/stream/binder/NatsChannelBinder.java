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

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.Consumer;
import io.nats.client.ErrorListener;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quick.meduo.nats.autoconfigure.NatsProperties;
import org.quick.meduo.stream.binder.properties.NatsBinderConfigurationProperties;
import org.quick.meduo.stream.binder.properties.NatsConsumerProperties;
import org.quick.meduo.stream.binder.properties.NatsExtendedBindingProperties;
import org.quick.meduo.stream.binder.properties.NatsProducerProperties;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * A NATS channel binder provides a NATS connection to the code attached to it.
 */
public class NatsChannelBinder extends
		AbstractMessageChannelBinder<ExtendedConsumerProperties<NatsConsumerProperties>, ExtendedProducerProperties<NatsProducerProperties>, NatsChannelProvisioner>
		implements ExtendedPropertiesBinder<MessageChannel, NatsConsumerProperties, NatsProducerProperties> {
			private static final Log logger = LogFactory.getLog(NatsChannelBinder.class);
	private final NatsExtendedBindingProperties bindingProperties;
	private NatsBinderConfigurationProperties properties;
	private NatsProperties natsProperties;
	private Connection connection;

	/**
	 * Create a binder with the specified properties. It is expected that either the NatsBinderConfigurationProperties will have a
	 * server url set, or the NatsProperties will. They are preferred in that order, and only one server URL or server URL list is used.
	 * The NatsProperties are considered global and a backup. If a connection or error listener is provided it is used, otherwise a default logging listener is assigned to avoid
	 * silent errors.
	 * @param bindingProperties extended properties for future use
	 * @param properties primary properties
	 * @param natsProperties backup global properties
	 * @param provisioningProvider provisioner for destination names
	 * @param connectionListener custom connection listener
	 * @param errorListener custom error listener
	 */
	public NatsChannelBinder(NatsExtendedBindingProperties bindingProperties,
			NatsBinderConfigurationProperties properties,
			NatsProperties natsProperties,
			NatsChannelProvisioner provisioningProvider,
			ConnectionListener connectionListener,
			ErrorListener errorListener) {
		super(null, provisioningProvider); // null for headers to embed
		this.bindingProperties = bindingProperties;
		this.properties = properties;
		this.natsProperties = natsProperties;

		try {
			Options.Builder builder = null;
			String bindingServer = (this.properties != null) ? this.properties.getServer() : null;
			String globalServer = (this.natsProperties != null) ? this.natsProperties.getServer() : null;

			// Use the binder properties first, if they don't have a server, try the global
			if (bindingServer != null && bindingServer.length() > 0) {
				logger.info("binder connecting to nats with named properties " + this.properties);
				builder = this.properties.toOptionsBuilder();
			}
			else if (globalServer != null && globalServer.length() > 0) {
				logger.info("binder connecting to nats with global properties " + this.natsProperties);
				builder = this.natsProperties.toOptionsBuilder();
			}
			else {
				this.connection = null;
				logger.info("unable to connect from binder to NATS no server properties where found");
				return;
			}

			if (connectionListener != null) {
				builder = builder.connectionListener(connectionListener);
			}
			else {
				builder = builder.connectionListener(new ConnectionListener() {
					@Override
					public void connectionEvent(Connection conn, Events type) {
							logger.info("NATS connection status changed " + type);
					}
				});
			}

			if (errorListener != null) {
				builder = builder.errorListener(errorListener);
			}
			else {
				builder = builder.errorListener(new ErrorListener() {
					@Override
					public void slowConsumerDetected(Connection conn, Consumer consumer) {
						logger.info("NATS connection slow consumer detected");
					}

					@Override
					public void exceptionOccurred(Connection conn, Exception exp) {
						logger.info("NATS connection exception occurred", exp);
					}

					@Override
					public void errorOccurred(Connection conn, String error) {
						logger.info("NATS connection error occurred " + error);
					}
				});
			}

			this.connection = Nats.connect(builder.build());
		}
		catch (Exception e) {
			logger.info("exception connecting binder to NATS", e);
			this.connection = null;
		}

		if (this.connection == null) {
			logger.info("unable to connect from binder to NATS");
		}
	}

	/**
	 * @return NATS connection
	 */
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	protected MessageHandler createProducerMessageHandler(ProducerDestination destination,
			ExtendedProducerProperties<NatsProducerProperties> producerProperties, MessageChannel errorChannel) {
		return new NatsMessageHandler(destination.getName(), this.connection);
	}

	@Override
	protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group,
			ExtendedConsumerProperties<NatsConsumerProperties> properties) {
		return new NatsMessageProducer((NatsConsumerDestination) destination, this.connection);
	}

	@Override
	protected PolledConsumerResources createPolledConsumerResources(String name, String group,
			ConsumerDestination destination, ExtendedConsumerProperties<NatsConsumerProperties> consumerProperties) {
		return new PolledConsumerResources(
				new NatsMessageSource((NatsConsumerDestination) destination, this.connection),
				registerErrorInfrastructure(destination, group, consumerProperties, true));
	}

	@Override
	public NatsConsumerProperties getExtendedConsumerProperties(String channelName) {
		return bindingProperties.getExtendedConsumerProperties(channelName);
	}

	@Override
	public NatsProducerProperties getExtendedProducerProperties(String channelName) {
		return bindingProperties.getExtendedProducerProperties(channelName);
	}

	@Override
	public String getDefaultsPrefix() {
		return bindingProperties.getDefaultsPrefix();
	}

	@Override
	public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
		return bindingProperties.getExtendedPropertiesEntryClass();
	}
}
