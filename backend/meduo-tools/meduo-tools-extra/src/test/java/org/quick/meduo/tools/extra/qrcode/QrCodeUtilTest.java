package org.quick.meduo.tools.extra.qrcode;

import org.quick.meduo.tools.core.codec.Base64;
import org.quick.meduo.tools.core.io.FileUtil;
import org.quick.meduo.tools.core.lang.Console;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.Color;
import java.io.File;

/**
 * 二维码工具类单元测试
 * 
 * @author looly
 *
 */
public class QrCodeUtilTest {

	@Test
	@Ignore
	public void generateTest() {
		QrCodeUtil.generate("https://hutool.cn/", 300, 300, FileUtil.file("e:/qrcode.jpg"));
	}

	@Test
	@Ignore
	public void generateCustomTest() {
		QrConfig config = new QrConfig();
		config.setMargin(0);
		config.setForeColor(Color.CYAN);
		// 背景色透明
		config.setBackColor(null);
		config.setErrorCorrection(ErrorCorrectionLevel.H);
		QrCodeUtil.generate("https://hutool.cn/", config, FileUtil.file("d:/qrcodeCustom.png"));
	}

	@Test
	@Ignore
	public void generateWithLogoTest() {
		QrCodeUtil.generate(//
				"http://hutool.cn/", //
				QrConfig.create().setImg("e:/pic/face.jpg"), //
				FileUtil.file("e:/qrcodeWithLogo.jpg"));
	}

	@Test
	@Ignore
	public void decodeTest() {
		String decode = QrCodeUtil.decode(FileUtil.file("e:/pic/qr.png"));
		Console.log(decode);
	}

	@Test
	@Ignore
	public void generateAsBase64Test(){
		String base64 = QrCodeUtil.generateAsBase64("http://hutool.cn/", new QrConfig(400, 400), "png");
		System.out.println(base64);

		byte[] bytes = FileUtil.readBytes(
			new File("d:/test/qr.png"));
		String encode = Base64.encode(bytes);
		String base641 = QrCodeUtil.generateAsBase64("http://hutool.cn/", new QrConfig(400, 400), "png", encode);
		System.out.println(base641);

	}

}
