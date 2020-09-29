package org.quick.meduo.tools.http.test;

import org.quick.meduo.tools.core.lang.Console;
import org.quick.meduo.tools.http.HttpRequest;
import org.quick.meduo.tools.http.HttpUtil;
import org.quick.meduo.tools.json.JSONUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Rest类型请求单元测试
 * 
 * @author looly
 *
 */
public class RestTest {

	@Test
	public void contentTypeTest() {
		HttpRequest request = HttpRequest.post("http://localhost:8090/rest/restTest/")//
				.body(JSONUtil.createObj()
						.set("aaa", "aaaValue")
						.set("键2", "值2").toString());
		Assert.assertEquals("application/json;charset=UTF-8", request.header("Content-Type"));
	}

	@Test
	@Ignore
	public void postTest() {
		HttpRequest request = HttpRequest.post("http://localhost:8090/rest/restTest/")//
				.body(JSONUtil.createObj()
						.set("aaa", "aaaValue")
						.set("键2", "值2").toString());
		Console.log(request.execute().body());
	}

	@Test
	@Ignore
	public void postTest2() {
		String result = HttpUtil.post("http://localhost:8090/rest/restTest/", JSONUtil.createObj()//
				.set("aaa", "aaaValue")
				.set("键2", "值2").toString());
		Console.log(result);
	}

	@Test
	@Ignore
	public void postTest3() {
		HttpRequest request = HttpRequest.post("http://211.162.39.204:8181/jeesite-simple/a/open/bizGwbnService/test")//
				.body(JSONUtil.createObj()
						.set("aaa", "aaaValue")
						.set("键2", "值2").toString());
		Console.log(request.execute().body());
	}
}
