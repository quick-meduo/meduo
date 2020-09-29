package org.quick.meduo.tools.db.nosql;

import org.quick.meduo.tools.db.nosql.redis.RedisDS;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisDSTest {

	@Test
	@Ignore
	public void redisDSTest(){
		final Jedis jedis = RedisDS.create().getJedis();
	}
}
