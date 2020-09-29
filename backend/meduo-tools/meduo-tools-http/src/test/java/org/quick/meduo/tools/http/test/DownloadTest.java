package org.quick.meduo.tools.http.test;

import org.junit.Ignore;
import org.junit.Test;

import org.quick.meduo.tools.core.io.FileUtil;
import org.quick.meduo.tools.core.io.StreamProgress;
import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.http.HttpRequest;
import org.quick.meduo.tools.http.HttpUtil;

/**
 * 下载单元测试
 * 
 * @author looly
 */
public class DownloadTest {

	@Test
	@Ignore
	public void downloadPicTest() {
		String url = "http://wx.qlogo.cn/mmopen/vKhlFcibVUtNBVDjcIowlg0X8aJfHXrTNCEFBukWVH9ta99pfEN88lU39MKspCUCOP3yrFBH3y2NbV7sYtIIlon8XxLwAEqv2/0";
		HttpUtil.downloadFile(url, "e:/pic/t3.jpg");
		Console.log("ok");
	}

	@Test
	@Ignore
	public void downloadSizeTest() {
		String url = "https://res.t-io.org/im/upload/img/67/8948/1119501/88097554/74541310922/85/231910/366466 - 副本.jpg";
		HttpRequest.get(url).setSSLProtocol("TLSv1.2").executeAsync().writeBody("e:/pic/366466.jpg");
	}

	@Test
	@Ignore
	public void downloadTest1() {
		long size = HttpUtil.downloadFile("http://explorer.bbfriend.com/crossdomain.xml", "e:/temp/");
		System.out.println("Download size: " + size);
	}

	@Test
	@Ignore
	public void downloadTest() {
		// 带进度显示的文件下载
		HttpUtil.downloadFile("http://mirrors.sohu.com/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso", FileUtil.file("d:/"), new StreamProgress() {

			final long time = System.currentTimeMillis();

			@Override
			public void start() {
				Console.log("开始下载。。。。");
			}

			@Override
			public void progress(long progressSize) {
				long speed = progressSize / (System.currentTimeMillis() - time) * 1000;
				Console.log("已下载：{}, 速度：{}/s", FileUtil.readableFileSize(progressSize), FileUtil.readableFileSize(speed));
			}

			@Override
			public void finish() {
				Console.log("下载完成！");
			}
		});
	}
}
