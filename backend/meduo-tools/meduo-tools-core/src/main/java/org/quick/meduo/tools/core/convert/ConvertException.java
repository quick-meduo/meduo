package org.quick.meduo.tools.core.convert;

import org.quick.meduo.tools.core.exceptions.ExceptionUtil;
import org.quick.meduo.tools.core.util.StrUtil;

/**
 * 转换异常
 * @author xiaoleilu
 */
public class ConvertException extends RuntimeException{
	private static final long serialVersionUID = 4730597402855274362L;

	public ConvertException(Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}
	
	public ConvertException(String message) {
		super(message);
	}
	
	public ConvertException(String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}
	
	public ConvertException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ConvertException(Throwable throwable, String messageTemplate, Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
