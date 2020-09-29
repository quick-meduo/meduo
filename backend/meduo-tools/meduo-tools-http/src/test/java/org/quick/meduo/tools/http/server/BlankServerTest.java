package org.quick.meduo.tools.http.server;

import org.quick.meduo.tools.http.HttpUtil;

public class BlankServerTest {
	public static void main(String[] args) {
		HttpUtil.createServer(8888)
				.addAction("/", (req, res)->{
					res.write("Hello Hutool Server");
				})
				.start();
	}
}
