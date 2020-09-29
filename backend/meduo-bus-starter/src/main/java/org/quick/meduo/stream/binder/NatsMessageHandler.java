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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import io.nats.client.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

/**
 * NatsMessageHandlers implement the standard message handling pattern. byte[], ByteBuffer and Strings are supported, where
 * Strings are treated as UTF8 bytes.
 */
public class NatsMessageHandler extends AbstractMessageHandler {
	private static final Log logger = LogFactory.getLog(NatsMessageHandler.class);

	private String subject;
	private Connection connection;

	/**
	 * Create a handler with a specific, unchanging subject, and a NATS connection.
	 * @param subject where to send message to by default
	 * @param nc NATS connection
	 */
	public NatsMessageHandler(String subject, Connection nc) {
		this.subject = subject;
		this.connection = nc;
	}

	@Override
	/**
	 * Given a message, take the payload and publish it on the handlers subject.
	 * If the message contains a NatsMessageProducer.REPLY_TO header, that subject is
	 * used in place of the hardcoded one. This allows a message handler to support request/reply.
	 */
	protected void handleMessageInternal(Message<?> message) {
		Object payload = message.getPayload();
		byte[] bytes = null;

		if (payload instanceof byte[]) {
			bytes = (byte[]) payload;
		}
		else if (payload instanceof ByteBuffer) {
			ByteBuffer buf = ((ByteBuffer) payload);
			bytes = new byte[buf.remaining()];
			buf.get(bytes);
		}
		else if (payload instanceof String) {
			bytes = ((String) payload).getBytes(StandardCharsets.UTF_8);
		}

		if (bytes == null) {
			logger.warn("NATS handler only supports byte array, byte buffer and string messages");
			return;
		}

		Object rt = message.getHeaders().get(NatsMessageProducer.REPLY_TO);
		String replyTo = rt != null ? rt.toString() : null;
		String subj = replyTo != null ? replyTo : this.subject;

		if (this.connection != null && subj != null && subj.length() > 0) {
			this.connection.publish(subj, bytes);
		}
	}
}
