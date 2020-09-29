package org.quick.meduo.tools.core.lang;

import org.junit.Assert;
import org.junit.Test;

import org.quick.meduo.tools.core.text.Simhash;
import org.quick.meduo.tools.core.util.StrUtil;

public class SimhashTest {
	
	@Test
	public void simTest() {
		String text1 = "我是 一个 普通 字符串";
		String text2 = "我是 一个 普通 字符串";
		
		Simhash simhash = new Simhash();
		long hash = simhash.hash(StrUtil.split(text1, ' '));
		Assert.assertTrue(hash != 0);
		
		simhash.store(hash);
		boolean duplicate = simhash.equals(StrUtil.split(text2, ' '));
		Assert.assertTrue(duplicate);
	}
}
