package org.quick.meduo.tools.log.test;

import org.junit.Test;

import org.quick.meduo.tools.log.StaticLog;

public class StaticLogTest {
	@Test
	public void test() {
		StaticLog.debug("This is static {} log", "debug");
		StaticLog.info("This is static {} log", "info");
	}
}
