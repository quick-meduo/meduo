package org.quick.meduo.tools.aop.aspects;

import org.quick.meduo.tools.core.date.TimeInterval;
import org.quick.meduo.tools.core.lang.Console;

import java.lang.reflect.Method;

/**
 * 通过日志打印方法的执行时间的切面
 *
 * @author Looly
 */
public class TimeIntervalAspect extends SimpleAspect {
	private static final long serialVersionUID = 1L;

	private final TimeInterval interval = new TimeInterval();

	@Override
	public boolean before(Object target, Method method, Object[] args) {
		interval.start();
		return true;
	}

	@Override
	public boolean after(Object target, Method method, Object[] args, Object returnVal) {
		Console.log("Method [{}.{}] execute spend [{}]ms return value [{}]",
				target.getClass().getName(), //
				method.getName(), //
				interval.intervalMs(), //
				returnVal);
		return true;
	}
}
