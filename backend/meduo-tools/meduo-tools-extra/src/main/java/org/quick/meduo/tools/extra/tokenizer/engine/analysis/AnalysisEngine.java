package org.quick.meduo.tools.extra.tokenizer.engine.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.quick.meduo.tools.core.util.StrUtil;
import org.quick.meduo.tools.extra.tokenizer.Result;
import org.quick.meduo.tools.extra.tokenizer.TokenizerEngine;
import org.quick.meduo.tools.extra.tokenizer.TokenizerException;

/**
 * Lucene-analysis分词抽象封装<br>
 * 项目地址：https://github.com/apache/lucene-solr/tree/master/lucene/analysis
 * 
 * @author looly
 *
 */
public class AnalysisEngine implements TokenizerEngine {

	private final Analyzer analyzer;

	/**
	 * 构造
	 * 
	 * @param analyzer 分析器{@link Analyzer}
	 */
	public AnalysisEngine(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	@Override
	public Result parse(CharSequence text) {
		TokenStream stream;
		try {
			stream = analyzer.tokenStream("text", StrUtil.str(text));
			stream.reset();
		} catch (IOException e) {
			throw new TokenizerException(e);
		}
		return new AnalysisResult(stream);
	}

}
