package org.quick.meduo.tools.extra.tokenizer.engine.ikanalyzer;

import org.quick.meduo.tools.extra.tokenizer.AbstractResult;
import org.quick.meduo.tools.extra.tokenizer.TokenizerException;
import org.quick.meduo.tools.extra.tokenizer.Word;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * IKAnalyzer分词结果实现<br>
 * 项目地址：https://github.com/yozhao/IKAnalyzer
 * 
 * @author looly
 *
 */
public class IKAnalyzerResult extends AbstractResult {

	private final IKSegmenter seg;

	/**
	 * 构造
	 * 
	 * @param seg 分词结果
	 */
	public IKAnalyzerResult(IKSegmenter seg) {
		this.seg = seg;
	}

	@Override
	protected Word nextWord() {
		Lexeme next;
		try {
			next = this.seg.next();
		} catch (IOException e) {
			throw new TokenizerException(e);
		}
		if (null != next) {
			return new IKAnalyzerWord(next);
		}
		return null;
	}
}
