package freela.util;

import java.awt.Container;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sun.util.resources.LocaleData;
import freela.util.Db.SelectCallbackTable;

public class Main extends JFrame {
	// current:more testing, date formatting
	JButton but;
	Grid table, tempoTable;

	public Main(final List<Map<String, String>> matches) {
		super("swing test");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);

		but = new JButton("blblbla");

		but.setLocation(20, 20);
		but.setSize(100, 20);

		// contentPane.add(but);

		setSize(1000, 600);
		contentPane.setSize(800, 500);
		setVisible(true);

		setLocationRelativeTo(null);
		table = new Grid();
		table.setSize(900, 400);
		table.setData(matches);

		contentPane.add(table);
		LocalDate date = LocalDate.now();
	
	}

	private void func(Function<Integer, String> object) {
		System.out.println(object.apply(2));
	}

	public static void main(String[] args) {
		setLookAndFell();

		EventQueue.invokeLater(() -> (enumsFromDb()));
	}

	private static void enumsFromDb() {
		Db.select(new Sql.Select("*").from("match").limit(100).get(),
				new SelectCallbackTable() {

					@Override
					public void callback(String[] columns,
							List<List<String>> data) {
						for (String string : columns) {
							System.out.print(string + " | ");
						}
						List<List<String>> gt10 = data.stream().filter(p -> {
							System.out.println("p.get12" + p.get(11));
							return Integer.parseInt(p.get(11)) > 10;

						}).collect(Collectors.toList());

						gt10.forEach(p -> {

							p.forEach(t -> {
								System.out.print(" " + t);
							});
							System.out.println("");

						});
						// System.out.println("\n=====================================");
						// for (List<String> list : data) {
						//
						// for (String string : list) {
						// System.out.print("public enum " + string + "{\n");
						// Db.select("show columns from `" + string + "`",
						// new Db.SelectCallback() {
						//
						// @Override
						// public void callback(ResultSet rs,
						// String[] columns2)
						// throws SQLException {
						// while (rs.next()) {
						//
						// System.out.print(rs
						// .getString("Field") + ",");
						//
						// System.out.println();
						//
						// }
						//
						// }
						// });
						// System.out.println("}");
						// }
						// System.out.println();
						// }
					}
				});

	}

	private static void blbla(Class<?> class1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -470384995502979244L;

	private static void setLookAndFell() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
	}
}
