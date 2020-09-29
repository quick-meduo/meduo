package org.quick.meduo.tools.poi.word;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.quick.meduo.tools.core.io.FileUtil;
import org.quick.meduo.tools.core.io.IORuntimeException;
import org.quick.meduo.tools.poi.exceptions.POIException;

/**
 * Word Document工具
 * 
 * @author looly
 * @since 4.4.1
 */
public class DocUtil {

	/**
	 * 创建{@link XWPFDocument}，如果文件已存在则读取之，否则创建新的
	 * 
	 * @param file docx文件
	 * @return {@link XWPFDocument}
	 */
	public static XWPFDocument create(File file) {
		try {
			return FileUtil.exist(file) ? new XWPFDocument(OPCPackage.open(file)) : new XWPFDocument();
		} catch (InvalidFormatException e) {
			throw new POIException(e);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
