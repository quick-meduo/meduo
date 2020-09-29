package org.quick.meduo.tools.http.server;

import org.quick.meduo.tools.core.swing.DesktopUtil;
import org.quick.meduo.tools.http.HttpUtil;

public class DocServerTest {

	public static void main(String[] args) {
		HttpUtil.createServer(80)
				// 设置默认根目录，
				.setRoot("D:\\workspace\\site\\hutool-site")
				.start();

		DesktopUtil.browse("http://localhost/");
	}
}
