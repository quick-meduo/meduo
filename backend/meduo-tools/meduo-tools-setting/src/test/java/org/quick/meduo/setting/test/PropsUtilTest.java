package org.quick.meduo.setting.test;

import org.quick.meduo.setting.dialect.PropsUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class PropsUtilTest {
	
	@Test
	public void getTest() {
		String driver = PropsUtil.get("test").getStr("driver");
		Assert.assertEquals("com.mysql.jdbc.Driver", driver);
	}

	@Test
	public void getFirstFoundTest() {
		String driver = Objects.requireNonNull(PropsUtil.getFirstFound("test2", "test")).getStr("driver");
		Assert.assertEquals("com.mysql.jdbc.Driver", driver);
	}
}
