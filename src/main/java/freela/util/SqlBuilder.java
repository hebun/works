package freela.util;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class SqlBuilder {
	protected String tableName;
	private Map<String, Object> params = new Hashtable<String, Object>();
	private Map<String, Object> where = new Hashtable<String, Object>();

	public SqlBuilder() {

	}

	public SqlBuilder(String string) {
		this.tableName = string;
	}

	public void put(String key, Object value) {
		params.put(key, value);

	}

	public void where(String key, Object value) {
		where.put(key, value);

	}

	public String getUpdate() {

		StringBuilder builder = new StringBuilder(" update `");
		builder.append(tableName);
		builder.append("` set ");
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append("='");
			builder.append(entry.getValue().toString());
			builder.append("',");

		}
		builder.deleteCharAt(builder.length() - 1);

		builder.append(" where ");
		if (where.size() > 0) {
			for (Entry<String, Object> entry : where.entrySet()) {
				builder.append(entry.getKey());
				builder.append("='");
				builder.append(entry.getValue().toString());
				builder.append("' and ");

			}
			builder.delete(builder.length() - 4, builder.length() - 1);

		} else if (whereSql != null && !whereSql.equals("")) {
			builder.append(whereSql);
		} else {
			throw new RuntimeException("cant use update without where clause");
		}
		return builder.toString();

	}

	public String getInsert() {
		StringBuilder builder = new StringBuilder("insert into `");
		builder.append(tableName);
		builder.append("`(");
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append(',');

		}
		builder.deleteCharAt(builder.length() - 1);

		builder.append(") values (");

		for (Entry<String, Object> entry : params.entrySet()) {
			builder.append('\'');
			builder.append(entry.getValue());
			builder.append('\'');
			builder.append(',');

		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(')');
		return builder.toString();
	}

	private String whereSql;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
}
