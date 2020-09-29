package org.quick.meduo.tools.poi.excel.sax;

import java.io.File;
import java.io.InputStream;

import org.quick.meduo.tools.poi.exceptions.POIException;

/**
 * Sax方式读取Excel接口，提供一些共用方法
 * @author looly
 *
 * @param <T> 子对象类型，用于标记返回值this
 * @since 3.2.0
 */
public interface ExcelSaxReader<T> {
	/**
	 * 开始读取Excel，读取所有sheet
	 * 
	 * @param path Excel文件路径
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(String path) throws POIException;

	/**
	 * 开始读取Excel，读取所有sheet
	 * 
	 * @param file Excel文件
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(File file) throws POIException;

	/**
	 * 开始读取Excel，读取所有sheet，读取结束后并不关闭流
	 * 
	 * @param in Excel包流
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(InputStream in) throws POIException;

	/**
	 * 开始读取Excel
	 * 
	 * @param path 文件路径
	 * @param rid Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(String path, int rid) throws POIException;

	/**
	 * 开始读取Excel
	 * 
	 * @param file Excel文件
	 * @param rid Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(File file, int rid) throws POIException;

	/**
	 * 开始读取Excel，读取结束后并不关闭流
	 * 
	 * @param in Excel流
	 * @param rid Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(InputStream in, int rid) throws POIException;
}
