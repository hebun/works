package freela.util;

import java.util.Hashtable;
import java.util.Map;

public abstract class Sql {

	public enum Operation {
		SELECT, UPDATE, INSERT, DELETE
	}

	protected String fieldList;
	protected Operation operation;
	protected String tableName;
	protected boolean isBuilt = false;
	protected String currentSql = "";
	protected Map<String, Map.Entry<String, String>> where = new Hashtable<String, Map.Entry<String, String>>();

	public Sql whereEntry(String type, final String key, final String value) {
		where.put(type, new Map.Entry<String, String>() {

			@Override
			public String getKey() {
				return key;
			}

			@Override
			public String getValue() {
				return value;
			}

			@Override
			public String setValue(String value) {

				return null;
			}
		});
		return this;
	}

	public Sql where(final String key, final String value) {

		return whereEntry("where", key, value);
	}

	public Sql and(final String key, final String value) {
		if (where.size() == 0)
			throw new RuntimeException("cant use and before where");
		return whereEntry("and", key, value);
	}

	public Sql or(final String key, final String value) {
		if (where.size() == 0)
			throw new RuntimeException("cant use or before where");
		return whereEntry("or", key, value);
	}

	public abstract String get();

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
							
				builder.append("'");
				builder.append(en.getValue().toString());
				builder.append("',");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
			this.currentSql = builder.toString();
			this.isBuilt = true;
			return currentSql;
		}

	}

	public static class Select extends Sql {

		public Select(String params) {
			fieldList = params;
			operation = Operation.SELECT;

		}

		public Select from(String table) {
			tableName = table;
			return this;
		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			StringBuilder builder = new StringBuilder("select ");
			builder.append(fieldList);
			builder.append(" from `");
			builder.append(this.tableName);
			builder.append("` ");

			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ');
				builder.append(en.getKey());
				builder.append(' ');
				builder.append(en.getValue().getKey());
				builder.append("'");
				builder.append(en.getValue().getValue());
				builder.append("' ");
			}
			this.currentSql = builder.toString();
			this.isBuilt = true;
			return currentSql;
		}
	}
}
