package org.quick.meduo.tools.captcha;

import org.junit.Ignore;
import org.junit.Test;

public class CaptchaUtilTest {
	
	@Test
	@Ignore
	public void createTest() {
		for(int i = 0; i < 1; i++) {
			CaptchaUtil.createShearCaptcha(320, 240);
		}
	}
}
