package org.quick.meduo.tools.core.swing;

import org.junit.Ignore;
import org.junit.Test;

import org.quick.meduo.tools.core.io.FileUtil;

public class RobotUtilTest {

	@Test
	@Ignore
	public void captureScreenTest() {
		RobotUtil.captureScreen(FileUtil.file("e:/screen.jpg"));
	}
}
