package freela.works;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

import freela.utilx.ASCIITable;
import freela.utilx.Db;
import freela.utilx.Sql;
import freela.utilx.Db.SelectCallbackLoop;
import freela.utilx.Sql.Select;

public class TestSql {

	public interface Call {
		public void callback();
	}

	public void anotherTest() {

		String[][] data = new String[10][];

		Db.select(new Sql.Select("*").from("product").limit(10).get(),
				new Db.SelectCallbackLoop() {
					int ind = 0;

					@Override
					public void callback(Map<String, String> map) {
						data[ind++] = map.values().toArray(
								new String[map.values().size()]);

					}
				});

		new ASCIITable().printTable(data);

		String input = "345";

		for (char i1 : input.toCharArray()) {
			System.out.print(i1 == '5' ? i1 : '0');

		}

		// System.out.println("5 digits here:" + x + y + z);
	}

	// Use an enum constructor, instance variable, and method.
	enum Apple {
		Jonathan(10), GoldenDel(9), RedDel(12), Winesap(15), Cortland(8);
		private int price; // price of each apple

		// Constructor
		Apple(int p) {
			System.out.println("enum cons"+p);
			price = p;
		}

		int getPrice() {
			return price;
		}
	}

	@Test
	public void test() {

		Apple ap;
		Class<? extends TestSql> class1=this.getClass();
		
		
	}

	// @Test
	public void testSql() {
		String sql = "select * from `product` ";

		String sql1 = new Sql.Select().from("product").get();
		assertEquals(sql, sql1);
	}

	private void doSomethingAndRunThisCode(Consumer<String> consumer) {
		System.out.println("here I am doing something");
		consumer.accept("blblbl");

	}

	private void func(Function<Integer, String> object) {
		System.out.println(object.apply(2));
	}

	public static double similarity(String s1, String s2) {
		if (s1.length() < s2.length()) { // s1 should always be bigger
			String swap = s1;
			s1 = s2;
			s2 = swap;
		}
		int bigLen = s1.length();
		if (bigLen == 0) {
			return 1.0; /* both strings are zero length */
		}
		return (bigLen - computeEditDistance(s1, s2)) / (double) bigLen;
	}

	public static int computeEditDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue),
									costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

}
