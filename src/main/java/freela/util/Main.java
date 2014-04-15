package freela.util;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {
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

	}

	public static void main(String[] args) {
		setLookAndFell();

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				String sql2 = new Sql.Insert("product").add("pname", "zzz")
						.add("content", "conten blabla lkdf")
						.add("pstate", "durum").get();

				System.out.println("sql2:" + sql2);
				// Db.select("select * from product", new
				// Db.SelectCallbackLoop() {
				//
				//
				// public void callback(Map<String, String> map) {
				// for (String en : map.values()) {
				// System.out.print(en);
				// }
				// System.out.println();
				// }
				// });
				//

				// Main frame = new Main( );

			}
		});
	}

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
