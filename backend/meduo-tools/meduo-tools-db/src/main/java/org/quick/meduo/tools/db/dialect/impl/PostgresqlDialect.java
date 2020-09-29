package org.quick.meduo.tools.db.dialect.impl;

import org.quick.meduo.tools.db.dialect.DialectName;
import org.quick.meduo.tools.db.sql.Wrapper;


/**
 * Postgree方言
 * @author loolly
 *
 */
public class PostgresqlDialect extends AnsiSqlDialect{
	private static final long serialVersionUID = 3889210427543389642L;

	public PostgresqlDialect() {
		wrapper = new Wrapper('"');
	}

	@Override
	public DialectName dialectName() {
		return DialectName.POSTGREESQL;
	}
}
