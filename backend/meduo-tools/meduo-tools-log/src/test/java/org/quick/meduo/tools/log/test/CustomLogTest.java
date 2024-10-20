package org.quick.meduo.tools.log.test;

import org.junit.Test;

import org.quick.meduo.tools.log.Log;
import org.quick.meduo.tools.log.LogFactory;
import org.quick.meduo.tools.log.dialect.commons.ApacheCommonsLogFactory;
import org.quick.meduo.tools.log.dialect.console.ConsoleLogFactory;
import org.quick.meduo.tools.log.dialect.jboss.JbossLogFactory;
import org.quick.meduo.tools.log.dialect.jdk.JdkLogFactory;
import org.quick.meduo.tools.log.dialect.log4j.Log4jLogFactory;
import org.quick.meduo.tools.log.dialect.log4j2.Log4j2LogFactory;
import org.quick.meduo.tools.log.dialect.slf4j.Slf4jLogFactory;
import org.quick.meduo.tools.log.dialect.tinylog.TinyLogFactory;

/**
 * 日志门面单元测试
 * @author Looly
 *
 */
public class CustomLogTest {
	
	private static final String LINE = "----------------------------------------------------------------------";
	
	@Test
	public void consoleLogTest(){
		LogFactory factory = new ConsoleLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void commonsLogTest(){
		LogFactory factory = new ApacheCommonsLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void tinyLogTest(){
		LogFactory factory = new TinyLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void log4j2LogTest(){
		LogFactory factory = new Log4j2LogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();

		log.debug(null);
		log.debug("This is custom '{}' log\n{}", factory.getName(), LINE);
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void log4jLogTest(){
		LogFactory factory = new Log4jLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
		
	}
	
	@Test
	public void jbossLogTest(){
		LogFactory factory = new JbossLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void jdkLogTest(){
		LogFactory factory = new JdkLogFactory();
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
	
	@Test
	public void slf4jTest(){
		LogFactory factory = new Slf4jLogFactory(false);
		LogFactory.setCurrentLogFactory(factory);
		Log log = LogFactory.get();
		
		log.info(null);
		log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
	}
}
