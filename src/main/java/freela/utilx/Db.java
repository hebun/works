package freela.utilx;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Db {

	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static String DB_URL = "jdbc:mysql://locxxalhost:3306cccx/fazxlastoklar?useUnicode=true&characterEncoding=utf8";
	public static String USER = "roxxxot";
	public static String PASS = "28xxx82";
	public static boolean started = false;
	static Connection conn = null;
	static Statement stmt = null;
	static int say = 0;

	public static void start(String caller) throws ClassNotFoundException,
			SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		stmt = conn.createStatement();

		started = true;
	}

	public static void select(String sql, SelectCallback callback) {

		try {
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			callback.callback(rs, columns);

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select");
			e.printStackTrace();
		} finally {
			close("");
		}

	}

	public static <T> List<T> select(String sql, Class<T> type) {

		try {
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}
			List<T> list = new ArrayList<T>();

			while (rs.next()) {
				T obj = type.newInstance();
				for (Field f : type.getDeclaredFields()) {
					if (Modifier.isPrivate(f.getModifiers())) {
						String input = f.getName();
						input = input.substring(0, 1).toUpperCase()
								+ input.substring(1);
						try {
							Method met = type.getMethod("set" + input,
									f.getType());

							met.invoke(obj, rs.getObject(f.getName()));
						} catch (NoSuchMethodException e) {

							e.printStackTrace();
						} catch (Exception e) {
						}
					}
				}
				list.add(obj);
			}
			return list;
		} catch (SQLException se) {
			System.out.println("se in select<T" + sql);
			se.printStackTrace();
			return new ArrayList<T>();
		} catch (Exception e) {
			System.out.println("e in select");
			e.printStackTrace();
			return new ArrayList<T>();
		} finally {
			close("");
		}

	}

	public static void select(String sql, SelectCallbackTable callback) {

		String[] columns = null;
		List<List<String>> data = null;
		try {
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			data = new ArrayList<List<String>>();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (String col : columns) {
					String string = rs.getString(col);
					if (string == null)
						string = "null";
					row.add(string);
				}
				data.add(row);
			}

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select" + sql);
			e.printStackTrace();
		} finally {
			close("");
		}
		callback.callback(columns, data);
	}

	public static void select(String sql, SelectCallbackLoop callback) {

		try {
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			while (rs.next()) {

				Map<String, String> map = new LinkedHashMap<String, String>();

				for (String col : columns) {
					String string = rs.getString(col);
					if (string == null)
						string = "null";
					map.put(col, string);

				}
				callback.callback(map);

			}

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select" + sql);
			e.printStackTrace();
		} finally {
			close("");
		}

	}

	public static List<Map<String, String>> selectTable(String sql) {

		List<Map<String, String>> list = null;

		try {
			start("");
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData data = rs.getMetaData();

			int colCount = data.getColumnCount();

			list = new ArrayList<Map<String, String>>();

			while (rs.next()) {

				Map<String, String> hash = new Hashtable<String, String>();
				for (int i = 1; i <= colCount; i++) {

					String value = rs.getString(i) == null ? "NULL" : rs
							.getString(i);
					hash.put(data.getColumnLabel(i), value);
				}
				list.add(hash);

			}
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close("");
		}// end try
		return list;
	}

	public static int insert(String sql) {

		try {
			start("query not started");
			say++;
			if (say % 100 == 0)
				System.out.print(say + ".");

			int rs = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (rs == 0)
				return rs;
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return (int) generatedKeys.getLong(1);
			}
			return 0;
		} catch (SQLException se) {
			System.out.println(sql);
			se.printStackTrace();
			return 0;
		} catch (Exception e) {

			System.out.println("query ex");
			return 0;
		} finally {

			close("");

		}// end trys

	}

	public static int update(String sql) {
		return insert(sql);
	}

	public static int delete(String sql) {
		return insert(sql);
	}

	public static void close(String caller) {

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		started = false;

	}
	public static Map<String, String> callProcedure(String sql) {
		try {
			start("");
			Map<String, String> map = new HashMap<String, String>();

			CallableStatement callableStatement = conn.prepareCall("{"+sql+"}");
			
			callableStatement.registerOutParameter("pcount", Types.INTEGER);
			callableStatement.registerOutParameter("ucount", Types.INTEGER);
			

		    boolean hadResults = callableStatement.execute();


		    while (hadResults) {
		        ResultSet rs = callableStatement.getResultSet();

		        // process result set
		      
		        hadResults = callableStatement.getMoreResults();
		    }
		    
		    map.put("pcount", callableStatement.getInt("pcount")+"");
		    map.put("ucount", callableStatement.getInt("ucount")+"");
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();

	}
	public static interface SelectCallback {
		public void callback(ResultSet rs, String[] columns)
				throws SQLException;

	}

	public static interface SelectCallbackLoop {

		public void callback(Map<String, String> map);
	}

	public static interface SelectCallbackTable {

		public void callback(String[] columns, List<List<String>> data);
	}

}
