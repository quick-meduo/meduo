package org.quick.meduo.system;

import org.quick.meduo.system.oshi.CpuInfo;
import org.quick.meduo.system.oshi.OshiUtil;
import org.junit.Assert;
import org.junit.Test;

public class OshiTest {
	
	@Test
	public void getMemeryTest() {
		long total = OshiUtil.getMemory().getTotal();
		Assert.assertTrue(total > 0);
	}

	@Test
	public void getCupInfo() {
		CpuInfo cpuInfo = OshiUtil.getCpuInfo();
		Assert.assertNotNull(cpuInfo);
	}
}
