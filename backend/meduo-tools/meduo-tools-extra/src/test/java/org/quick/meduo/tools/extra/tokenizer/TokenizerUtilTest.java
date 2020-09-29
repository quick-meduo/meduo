package org.quick.meduo.tools.extra.tokenizer;

import org.quick.meduo.tools.core.collection.IterUtil;
import org.quick.meduo.tools.extra.tokenizer.engine.analysis.SmartcnEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.hanlp.HanLPEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.ikanalyzer.IKAnalyzerEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.jcseg.JcsegEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.jieba.JiebaEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.mmseg.MmsegEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.mynlp.MynlpEngine;
import org.quick.meduo.tools.extra.tokenizer.engine.word.WordEngine;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;

/**
 * 模板引擎单元测试
 * 
 * @author looly
 *
 */
public class TokenizerUtilTest {
	
	String text = "这两个方法的区别在于返回值";

	@Test
	public void createEngineTest() {
		// 默认分词引擎，此处为Ansj
		TokenizerEngine engine = TokenizerUtil.createEngine();
		Result result = engine.parse(text);
		checkResult(result);
	}
	
	@Test
	public void hanlpTest() {
		TokenizerEngine engine = new HanLPEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这 两 个 方法 的 区别 在于 返回 值", resultStr);
	}
	
	@Test
	public void ikAnalyzerTest() {
		TokenizerEngine engine = new IKAnalyzerEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这两个 方法 的 区别 在于 返回值", resultStr);
	}
	
	@Test
	public void jcsegTest() {
		TokenizerEngine engine = new JcsegEngine();
		Result result = engine.parse(text);
		checkResult(result);
	}
	
	@Test
	public void jiebaTest() {
		TokenizerEngine engine = new JiebaEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这 两个 方法 的 区别 在于 返回值", resultStr);
	}
	
	@Test
	public void mmsegTest() {
		TokenizerEngine engine = new MmsegEngine();
		Result result = engine.parse(text);
		checkResult(result);
	}
	
	@Test
	public void smartcnTest() {
		TokenizerEngine engine = new SmartcnEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这 两 个 方法 的 区别 在于 返回 值", resultStr);
	}
	
	@Test
	public void wordTest() {
		TokenizerEngine engine = new WordEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这两个 方法 的 区别 在于 返回值", resultStr);
	}
	
	@Test
	@Ignore
	public void mynlpTest() {
		// 此单元测试需要JDK8，默认忽略
		TokenizerEngine engine = new MynlpEngine();
		Result result = engine.parse(text);
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这 两个 方法 的 区别 在于 返回 值", resultStr);
	}
	
	private void checkResult(Result result) {
		String resultStr = IterUtil.join((Iterator<Word>)result, " ");
		Assert.assertEquals("这 两个 方法 的 区别 在于 返回 值", resultStr);
	}
}
