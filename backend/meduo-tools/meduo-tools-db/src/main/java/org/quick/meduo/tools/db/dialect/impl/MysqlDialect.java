package org.quick.meduo.tools.db.dialect.impl;

import org.quick.meduo.tools.db.Page;
import org.quick.meduo.tools.db.dialect.DialectName;
import org.quick.meduo.tools.db.sql.SqlBuilder;
import org.quick.meduo.tools.db.sql.Wrapper;

/**
 * MySQL方言
 * @author loolly
 *
 */
public class MysqlDialect extends AnsiSqlDialect{
	private static final long serialVersionUID = -3734718212043823636L;

	public MysqlDialect() {
		wrapper = new Wrapper('`');
	}

	@Override
	protected SqlBuilder wrapPageSql(SqlBuilder find, Page page) {
		return find.append(" LIMIT ").append(page.getStartPosition()).append(", ").append(page.getPageSize());
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.MYSQL;
	}
}
