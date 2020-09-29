package org.quick.meduo.tools.db;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;

import org.quick.meduo.tools.core.collection.CollUtil;
import org.quick.meduo.tools.db.ds.DSFactory;
import org.quick.meduo.tools.db.ds.c3p0.C3p0DSFactory;
import org.quick.meduo.tools.db.ds.dbcp.DbcpDSFactory;
import org.quick.meduo.tools.db.ds.druid.DruidDSFactory;
import org.quick.meduo.tools.db.ds.hikari.HikariDSFactory;
import org.quick.meduo.tools.db.ds.pooled.PooledDSFactory;
import org.quick.meduo.tools.db.ds.tomcat.TomcatDSFactory;

/**
 * 数据源单元测试
 * 
 * @author Looly
 *
 */
public class DsTest {

	@Test
	public void defaultDsTest() throws SQLException {
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void hikariDsTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new HikariDSFactory());
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void druidDsTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new DruidDSFactory());
		DataSource ds = DSFactory.get("test");

		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void tomcatDsTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new TomcatDSFactory());
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void dbcpDsTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new DbcpDSFactory());
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void c3p0DsTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new C3p0DSFactory());
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}

	@Test
	public void hutoolPoolTest() throws SQLException {
		DSFactory.setCurrentDSFactory(new PooledDSFactory());
		DataSource ds = DSFactory.get("test");
		Db db = Db.use(ds);
		List<Entity> all = db.findAll("user");
		Assert.assertTrue(CollUtil.isNotEmpty(all));
	}
}
