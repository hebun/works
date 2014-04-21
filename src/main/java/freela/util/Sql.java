package freela.util;

import java.util.Hashtable;
import java.util.Map;

public abstract class Sql {
	protected String fieldList;

	protected String tableName;
	protected boolean isBuilt = false;
	protected String currentSql = "";
	protected String orderColumn = null;
	protected String orderWay = "asc";
	protected int start = 0, count = 0;
	protected Map<String, Map.Entry<String, String>> where = new Hashtable<String, Map.Entry<String, String>>();

	public Sql whereEntry(String type, final String key, final Object value) {
		where.put(type, new Map.Entry<String, String>() {

			@Override
			public String getKey() {
				return key;
			}

			@Override
			public String getValue() {
				return value.toString();
			}

			@Override
			public String setValue(String value) {

				return null;
			}
		});
		return this;
	}

	public Sql where(final String key, final Object value) {

		return whereEntry("where", key, value);
	}

	public Sql and(final String key, final String value) {
		if (where.size() == 0)
			throw new SqlBuilderExeption("cant use and before where");
		return whereEntry("and", key, value);
	}

	public Sql or(final String key, final String value) {
		if (where.size() == 0)
			throw new SqlBuilderExeption("cant use or before where");

		return whereEntry("or", key, value);
	}

	public Sql desc() {
		this.orderWay = "desc";
		return this;
	}

	public Sql limit(int start, int count) {
		this.start = start;
		this.count = count;
		return this;
	}

	public Sql limit(int count) {

		return this.limit(0, count);
	}

	public String getFollowings() {
		String ret = "";

		if (orderColumn != null) {
			ret += " order by " + orderColumn + " " + orderWay;
		}
		if (count != 0) {
			ret += " limit " + start + "," + count;
		}
		return ret;
	}

	public abstract String get();

	public static class Delete extends Sql {

		public Delete(String table) {
			this.tableName = table;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			if (where.size() == 0)
				throw new RuntimeException("cant use delete without where");
			StringBuilder builder = new StringBuilder("delete from  `");
			builder.append(this.tableName);
			builder.append("`");
			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ').append(en.getKey()).append(' ')
						.append(en.getValue().getKey()).append("'")
						.append(en.getValue().getValue()).append("' ");
			}

			this.currentSql = builder.toString();
			this.isBuilt = true;
			this.currentSql += super.getFollowings();
			return currentSql;
		}

	}

	public static class Update extends Sql {
		Map<String, Object> fields = new Hashtable<String, Object>();

		public Update(String table) {
			this.tableName = table;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			if (where.size() == 0)
				throw new RuntimeException("cant use update without where");
			StringBuilder builder = new StringBuilder("update `");
			builder.append(this.tableName);
			builder.append("` set ");
			for (Map.Entry<String, Object> en : fields.entrySet()) {
				builder.append(en.getKey()).append("='")
						.append(en.getValue().toString()).append("',");
			}
			builder.deleteCharAt(builder.length() - 1);
			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ').append(en.getKey()).append(' ')
						.append(en.getValue().getKey()).append("'")
						.append(en.getValue().getValue()).append("' ");
			}

			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

		public Update add(String key, Object value) {

			this.fields.put(key, value);
			return this;

		}
	}

	public static class Insert extends Sql {
		Map<String, Object> fields = new Hashtable<String, Object>();

		public Insert(String table) {
			this.tableName = table;

		}

		public Insert add(String key, Object value) {

			this.fields.put(key, value);
			return this;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			StringBuilder builder = new StringBuilder("insert into `");
			builder.append(this.tableName);
			builder.append("`(");
			for (Map.Entry<String, Object> en : fields.entrySet()) {
				builder.append(en.getKey());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(") values (");
			for (Map.Entry<String, Object> en : fields.entrySet()) {

				builder.append("'").append(en.getValue().toString())
						.append("',");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

	}

	public static class Select extends Sql {

		public Select(String params) {
			setFields(params);

		}

		public Select() {
			this("*");

		}

		public Select setFields(String params) {
			this.isBuilt = false;
			fieldList = params;

			return this;
		}

		public Select from(String table) {
			this.isBuilt = false;
			tableName = table;
			return this;
		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			StringBuilder builder = new StringBuilder("select ");
			builder.append(fieldList).append(" from `").append(this.tableName)
					.append("` ");

			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ').append(en.getKey()).append(' ')
						.append(en.getValue().getKey()).append("'")
						.append(en.getValue().getValue()).append("' ");
			}
			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

		public Select order(String order) {

			this.orderColumn = order;
			return this;
		}
	}

	@SuppressWarnings("serial")
	public class SqlBuilderExeption extends RuntimeException {

		public SqlBuilderExeption(String string) {
			super(string);
		}

	}
}
