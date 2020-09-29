package org.quick.meduo.tools.extra.pinyin;

import org.quick.meduo.tools.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;
import org.junit.Assert;
import org.junit.Test;

public class PinyinUtilTest {

	@Test
	public void getPinyinTest(){
		final String pinyin = PinyinUtil.getPinyin("你好", " ");
		Assert.assertEquals("ni hao", pinyin);
	}

	@Test
	public void getPinyinByPinyin4jTest() {
		final Pinyin4jEngine engine = new Pinyin4jEngine();
		final String pinyin = engine.getPinyin("你好h", " ");
		Assert.assertEquals("ni hao h", pinyin);
	}

	@Test
	public void getPinyinUpperCaseTest(){
		final String pinyin = PinyinUtil.getPinyin("你好怡", " ");
		Assert.assertEquals("ni hao yi", pinyin);
	}

	@Test
	public void getFirstLetterTest(){
		final String result = PinyinUtil.getFirstLetter("H是第一个", ", ");
		Assert.assertEquals("h, s, d, y, g", result);
	}

	@Test
	public void getFirstLetterByPinyin4jTest(){
		final Pinyin4jEngine engine = new Pinyin4jEngine();
		final String result = engine.getFirstLetter("林海", "");
		Assert.assertEquals("lh", result);
	}
}
