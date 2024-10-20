package org.quick.meduo.tools.cron.demo;

import org.junit.Ignore;
import org.junit.Test;

import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.core.thread.ThreadUtil;
import org.quick.meduo.tools.cron.CronUtil;
import org.quick.meduo.tools.cron.task.Task;

/**
 * 定时任务样例
 */
public class CronTest {

	@Test
	@Ignore
	public void customCronTest() {
		CronUtil.schedule("*/2 * * * * *", (Task) () -> Console.log("Task excuted."));

		// 支持秒级别定时任务
		CronUtil.setMatchSecond(true);
		CronUtil.start();
	}

	@Test
	@Ignore
	public void cronTest() {
		// 支持秒级别定时任务
		CronUtil.setMatchSecond(true);
		CronUtil.getScheduler().setDaemon(false);
		CronUtil.start();

		ThreadUtil.sleep(3000);
		CronUtil.stop();
	}
	
	@Test
	@Ignore
	public void cronTest2() {
		// 支持秒级别定时任务
		CronUtil.setMatchSecond(true);
		CronUtil.start();

		ThreadUtil.waitForDie();
		Console.log("Exit.");
	}

	@Test
//	@Ignore
	public void addAndRemoveTest() {
		String id = CronUtil.schedule("*/2 * * * * *", (Runnable) () -> Console.log("task running : 2s"));

		Console.log(id);
		CronUtil.remove(id);

		// 支持秒级别定时任务
		CronUtil.setMatchSecond(true);
		CronUtil.start();
	}
}
