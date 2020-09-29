package org.quick.meduo.tools.core.net;

import org.quick.meduo.tools.core.net.url.UrlQuery;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

public class UrlQueryTest {

	@Test
	public void parseTest(){
		String queryStr = "a=1&b=111==";
		UrlQuery q = new UrlQuery();
		UrlQuery parse = q.parse(queryStr, Charset.defaultCharset());
		Assert.assertEquals("111==", parse.get("b"));
		Assert.assertEquals("a=1&b=111==", parse.toString());
	}
}
