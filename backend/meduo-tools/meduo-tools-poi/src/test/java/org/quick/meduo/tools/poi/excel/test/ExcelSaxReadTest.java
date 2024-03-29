package org.quick.meduo.tools.poi.excel.test;

import org.quick.meduo.tools.core.collection.CollUtil;
import org.quick.meduo.tools.core.convert.Convert;
import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.core.util.StrUtil;
import org.quick.meduo.tools.poi.excel.ExcelUtil;
import org.quick.meduo.tools.poi.excel.sax.Excel03SaxReader;
import org.quick.meduo.tools.poi.excel.sax.handler.RowHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Excel sax方式读取
 *
 * @author looly
 */
public class ExcelSaxReadTest {

	@Test
	public void excel07Test() {
		// 工具化快速读取
		ExcelUtil.read07BySax("aaa.xlsx", 0, createRowHandler());
	}

	@Test
	public void excel03Test() {
		Excel03SaxReader reader = new Excel03SaxReader(createRowHandler());
		reader.read("aaa.xls", 1);
		// Console.log("Sheet index: [{}], Sheet name: [{}]", reader.getSheetIndex(), reader.getSheetName());
		ExcelUtil.read03BySax("aaa.xls", 1, createRowHandler());
	}

	@Test
	@Ignore
	public void readBlankLineTest() {
		ExcelUtil.readBySax("e:/ExcelBlankLine.xlsx", 0, (sheetIndex, rowIndex, rowList) -> {
			if (StrUtil.isAllEmpty(Convert.toStrArray(rowList))) {
				return;
			}
			Console.log(rowList);
		});
	}

	@Test
	public void readBySaxTest() {
		ExcelUtil.readBySax("blankAndDateTest.xlsx", 0, createRowHandler());
	}

	@Test
	@Ignore
	public void readBySaxTest2() {
		ExcelUtil.readBySax("e:/B23_20180404164901240.xlsx", 2, (sheetIndex, rowIndex, rowList) -> Console.log(rowList));
	}

	private RowHandler createRowHandler() {
		return (sheetIndex, rowIndex, rowlist) -> {
//				Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
			if (5 != rowIndex && 6 != rowIndex) {
				// 测试样例中除第五行、第六行都为非空行
				Assert.assertTrue(CollUtil.isNotEmpty(rowlist));
			}
		};
	}

	@Test
	@Ignore
	public void handle07CellTest() {
		ExcelUtil.readBySax("d:/test/test.xlsx", -1, new RowHandler() {

					@Override
					public void handleCell(int sheetIndex, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {
						Console.log("{} {} {}", rowIndex, cellIndex, value);
					}

					@Override
					public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {

					}
				}
		);
	}

	@Test
	@Ignore
	public void handle03CellTest() {
		ExcelUtil.readBySax("d:/test/test.xls", -1, new RowHandler() {

					@Override
					public void handleCell(int sheetIndex, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {
						Console.log("{} {} {}", rowIndex, cellIndex, value);
					}

					@Override
					public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
					}
				}
		);
	}

	@Test
	@Ignore
	public void dateReadTest(){
		ExcelUtil.readBySax("d:/test/sax_date_test.xlsx", 0, (i, i1, list) ->
				Console.log(StrUtil.join(", ", list)));
	}

	@Test
	@Ignore
	public void readBlankTest(){
		File file = new File("D:/test/b.xlsx");

		ExcelUtil.readBySax(file, 0, (sheetIndex, rowIndex, rowList) -> rowList.forEach(Console::log));

		ExcelUtil.getReader(file).read().forEach(Console::log);
	}
}
