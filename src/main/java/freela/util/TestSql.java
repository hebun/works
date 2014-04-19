package freela.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

public class TestSql {

	public interface Call {
		public void callback();
	}

	@Test
	public void test() {

		func((o) -> {

			return o.toString();
		});
		List<String> list=new ArrayList<>();
		// String sql = "select count(*) from catasto where series = 1 ";
		// String mysql = new Sql.Select("count(*)").from("catasto")
		// .where("series=", 1).get();
		// assertEquals(similarity(sql, mysql), 1.0, 0.3);
		// assertEquals(sql, mysql);
		doSomethingAndRunThisCode(new Consumer<String>() {

			@Override
			public void accept(String t) {
				// TODO Auto-generated method stub

			}
		});

		doSomethingAndRunThisCode((s) -> {
			System.out.println(s);
		}

		);
		// doSomethingAndRunThisCode();

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
