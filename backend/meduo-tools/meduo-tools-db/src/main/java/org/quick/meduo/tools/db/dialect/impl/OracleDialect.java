package org.quick.meduo.tools.db.dialect.impl;

import org.quick.meduo.tools.db.Page;
import org.quick.meduo.tools.db.dialect.DialectName;
import org.quick.meduo.tools.db.sql.SqlBuilder;

/**
 * Oracle 方言
 * @author loolly
 *
 */
public class OracleDialect extends AnsiSqlDialect{
	private static final long serialVersionUID = 6122761762247483015L;

	public OracleDialect() {
//		wrapper = new Wrapper('"');	//Oracle所有字段名用双引号包围，防止字段名或表名与系统关键字冲突
	}
	
	@Override
	protected SqlBuilder wrapPageSql(SqlBuilder find, Page page) {
		final int[] startEnd = page.getStartEnd();
		return find
			.insertPreFragment("SELECT * FROM ( SELECT row_.*, rownum rownum_ from ( ")
			.append(" ) row_ where rownum <= ").append(startEnd[1])//
			.append(") table_alias")//
			.append(" where table_alias.rownum_ > ").append(startEnd[0]);//
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.ORACLE;
	}
}
