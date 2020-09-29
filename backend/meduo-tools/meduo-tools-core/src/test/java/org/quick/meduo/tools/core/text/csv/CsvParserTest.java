package org.quick.meduo.tools.core.text.csv;

import org.quick.meduo.tools.core.io.IoUtil;
import org.quick.meduo.tools.core.util.StrUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class CsvParserTest {
	
	@Test
	public void parseTest1() {
		StringReader reader = StrUtil.getReader("aaa,b\"bba\",ccc");
		CsvParser parser = new CsvParser(reader, null);
		CsvRow row = parser.nextRow();
		//noinspection ConstantConditions
		Assert.assertEquals("b\"bba\"", row.getRawList().get(1));
		IoUtil.close(parser);
	}
	
	@Test
	public void parseTest2() {
		StringReader reader = StrUtil.getReader("aaa,\"bba\"bbb,ccc");
		CsvParser parser = new CsvParser(reader, null);
		CsvRow row = parser.nextRow();
		//noinspection ConstantConditions
		Assert.assertEquals("\"bba\"bbb", row.getRawList().get(1));
		IoUtil.close(parser);
	}
	
	@Test
	public void parseTest3() {
		StringReader reader = StrUtil.getReader("aaa,\"bba\",ccc");
		CsvParser parser = new CsvParser(reader, null);
		CsvRow row = parser.nextRow();
		//noinspection ConstantConditions
		Assert.assertEquals("bba", row.getRawList().get(1));
		IoUtil.close(parser);
	}
	
	@Test
	public void parseTest4() {
		StringReader reader = StrUtil.getReader("aaa,\"\",ccc");
		CsvParser parser = new CsvParser(reader, null);
		CsvRow row = parser.nextRow();
		//noinspection ConstantConditions
		Assert.assertEquals("", row.getRawList().get(1));
		IoUtil.close(parser);
	}
}
