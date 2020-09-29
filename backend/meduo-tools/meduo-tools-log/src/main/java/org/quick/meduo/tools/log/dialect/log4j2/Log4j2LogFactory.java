package org.quick.meduo.tools.log.dialect.log4j2;

import org.quick.meduo.tools.log.Log;
import org.quick.meduo.tools.log.LogFactory;

/**
 * <a href="http://logging.apache.org/log4j/2.x/index.html">Apache Log4J 2</a> log.<br>
 * @author Looly
 *
 */
public class Log4j2LogFactory extends LogFactory{
	
	public Log4j2LogFactory() {
		super("Log4j2");
		checkLogExist(org.apache.logging.log4j.LogManager.class);
	}

	@Override
	public Log createLog(String name) {
		return new Log4j2Log(name);
	}

	@Override
	public Log createLog(Class<?> clazz) {
		return new Log4j2Log(clazz);
	}

}
