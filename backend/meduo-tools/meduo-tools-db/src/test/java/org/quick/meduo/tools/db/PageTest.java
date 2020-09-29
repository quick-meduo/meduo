package org.quick.meduo.tools.db;

import org.quick.meduo.tools.db.sql.Order;
import org.junit.Assert;
import org.junit.Test;

public class PageTest {

	@Test
	public void addOrderTest() {
		Page page = new Page();
		page.addOrder(new Order("aaa"));
		Assert.assertEquals(page.getOrders().length, 1);
		page.addOrder(new Order("aaa"));
		Assert.assertEquals(page.getOrders().length, 2);
	}
}
