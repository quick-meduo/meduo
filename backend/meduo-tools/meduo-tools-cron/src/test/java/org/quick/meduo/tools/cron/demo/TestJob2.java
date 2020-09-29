package org.quick.meduo.tools.cron.demo;

import java.util.concurrent.TimeUnit;

import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.core.thread.ThreadUtil;

/**
 * 测试定时任务，当触发到定时的时间点时，执行doTest方法
 * 
 * @author looly
 *
 */
public class TestJob2 {

	/**
	 * 执行定时任务内容
	 */
	public void doTest() {
		Console.log("TestJob2.doTest开始执行……");
		ThreadUtil.sleep(20, TimeUnit.SECONDS);
		Console.log("延迟20s打印testJob2");
	}
}
