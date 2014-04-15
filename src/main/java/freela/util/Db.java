package freela.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Db {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/fazlastoklar";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "2882";
	public static boolean started = false;
	static Connection conn = null;
	static Statement stmt = null;
	static int say = 0;

	public static void start(String caller) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		started = true;
	}

	public static void select(String sql, SelectCallback callback) {
		start("");

		try {

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

	public static void select(String sql, SelectCallbackLoop callback) {
		start("");

		try {

			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			while (rs.next()) {

				Map<String, String> map = new Hashtable<String, String>();

				for (String col : columns) {
					String string = rs.getString(col);
					if(string==null) string="null";
					map.put(col, string);

				}
				callback.callback(map);

			}

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select"+sql);
			e.printStackTrace();
		} finally {
			close("");
		}

	}

	public static List<Map<String, String>> selectTable(String sql) {
		start("");

		List<Map<String, String>> list = null;

		try {

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
		start("query not started");
		try {
			say++;
			if (say % 100 == 0)
				System.out.print(say + ".");

			int rs = stmt.executeUpdate(sql);
			return rs;

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

	public static interface SelectCallback {
		public void callback(ResultSet rs, String[] columns)
				throws SQLException;

	}

	public static interface SelectCallbackLoop {

		public void callback(Map<String, String> map);
	}

}
