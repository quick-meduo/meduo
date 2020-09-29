package org.quick.meduo.tools.crypto.test;

import org.quick.meduo.tools.crypto.Mode;
import org.quick.meduo.tools.crypto.Padding;
import org.quick.meduo.tools.crypto.symmetric.AES;
import org.junit.Assert;
import org.junit.Test;

public class AESTest {

	@Test
	public void encryptTest() {
		// 构建
		AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
				"1234567890123456".getBytes(), "1234567890123456".getBytes());
		String encryptHex = aes.encryptHex("123456");
		Assert.assertEquals("d637735ae9e21ba50cb686b74fab8d2c", encryptHex);
	}

}
