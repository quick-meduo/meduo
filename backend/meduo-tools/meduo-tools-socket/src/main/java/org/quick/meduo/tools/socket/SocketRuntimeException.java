package org.quick.meduo.tools.socket;

import org.quick.meduo.tools.core.exceptions.ExceptionUtil;
import org.quick.meduo.tools.core.util.StrUtil;

/**
 * Socket异常
 * 
 * @author xiaoleilu
 */
public class SocketRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 8247610319171014183L;

	public SocketRuntimeException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}

	public SocketRuntimeException(String message) {
		super(message);
	}

	public SocketRuntimeException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}

	public SocketRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SocketRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
