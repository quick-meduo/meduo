package org.quick.meduo.setting.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.setting.Setting;

/**
 * Setting单元测试
 * @author Looly
 *
 */
public class SettingTest {
	
	@Test
	public void settingTest(){
		Setting setting = new Setting("test.setting", true);
		
		String driver = setting.getByGroup("driver", "demo");
		Assert.assertEquals("com.mysql.jdbc.Driver", driver);
		
		//本分组变量替换
		String user = setting.getByGroup("user", "demo");
		Assert.assertEquals("rootcom.mysql.jdbc.Driver", user);
		
		//跨分组变量替换
		String user2 = setting.getByGroup("user2", "demo");
		Assert.assertEquals("rootcom.mysql.jdbc.Driver", user2);
		
		//默认值测试
		String value = setting.getStr("keyNotExist", "defaultTest");
		Assert.assertEquals("defaultTest", value);
	}
	
	@Test
	@Ignore
	public void settingTestForAbsPath(){
		Setting setting = new Setting("d:\\excel-plugin\\other.setting", true);
		Console.log(setting.getStr("a"));
	}
	
	@Test
	public void settingTestForCustom() {
		Setting setting = new Setting();
		
		setting.put("group1", "user", "root");
		setting.put("group2", "user", "root2");
		setting.put("group3", "user", "root3");
		setting.set("user", "root4");
		
		Assert.assertEquals("root", setting.getByGroup("user", "group1"));
		Assert.assertEquals("root2", setting.getByGroup("user", "group2"));
		Assert.assertEquals("root3", setting.getByGroup("user", "group3"));
		Assert.assertEquals("root4", setting.get("user"));
	}
}
