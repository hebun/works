package freela.works;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import freela.utilx.Sql;
import freela.utilx.Sql.Select;

public class xxx implements Runnable {
	private static final char ESC_CODE = 'x';;
	String name;
	Thread thread;
	int speed = 0;
	int no;
	long cc = 0;

	@Inject
	Say say;

	public String get() {
		cc++;
		return name;
	}

	public xxx(String name, int no) {
		this.no = no;
		this.name = name;
		Random random = new Random();
		speed = Math.abs(random.nextInt() % 100);

		this.thread = new Thread(this, name);

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAnno {
		String str();

		int val();
	}

	public static void main(String[] args) {

		xxx x = new xxx("name", 0);
		
		x.getSay().say();		

		// sqljoinWork();

		// somestuf();

		//
		// Gen gen = new Gen();
		//
		// for (int i : gen) {
		// System.out.println(i);
		// }

		// processWork();

		// memoryWork();

		// Gen<Integer> g=new Gen<>(3);

		// listRecursive(new File("."));

		// fileReadWriteWork();
		// annotationWork();
		// threadWork();
	}

	public Say getSay() {
		return say;
	}

	public void setSay(Say say) {
		this.say = say;
	}

	private static void sqljoinWork() {
		Sql sel = new Select().from("talep").as("t").rightJoin("product")
				.as("p").on("t.productid", "p.id").innerJoin("user").as("u")
				.on("u.id", "p.userid").where("t.id<", 10);

		System.out.println(sel.get());
	}

	private static void somestuf() {
		TreeSet<Integer> treeSet = new TreeSet<>((o1, o2) -> {
			return o1 < o2 ? 1 : o1 == o2 ? 0 : -1;
		}

		);
		List<String> coreModules = Arrays.asList("TOOLBAR_TO_DO_LIST",
				"TOOLBAR_PROPERTY", "TOOLBAR_PEOPLE", "TOOLBAR_INSURANCE",
				"TOOLBAR_BATCH", "TOOLBAR_INFORMATION_REFERENCE",
				"TOOLBAR_LR_PROPERTY", "TOOLBAR_CASE_FOLDER",
				"TOOLBAR_INSPECTION_RESULT", "TOOLBAR_MY_OFFICE");
		coreModules.add("blblba");
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			treeSet.add(random.nextInt(100));
		}
		treeSet.stream().forEach(System.out::println);

		Integer[] arr = { 3, 23, 4, 22, 45, 333, 22, 3 };

		List<Integer> li = new ArrayList<Integer>(Arrays.asList(arr));
	}

	private static void processWork() {
		System.out.println(System.getenv("SESSION"));

		long start, end;
		start = System.currentTimeMillis(); // get starting time
		for (int i = 0; i < 10000000; i++) {
			int x = 0;
			int y = x + 2;
		}

		end = System.currentTimeMillis(); // get ending time
		System.out.println("Elapsed time: " + (end - start));

		Runtime r = Runtime.getRuntime();

		Process p = null;

		try {
			p = r.exec("ls");
			System.out.println("execed");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));
			String line = null;
			System.out.println("<OUTPUT>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</OUTPUT>");
			p.waitFor();
			System.out.println("finished");
		} catch (Exception e) {

		}
	}

	private static void memoryWork() {
		Runtime r = Runtime.getRuntime();
		long mem1, mem2;

		System.out.println("Total memory is: " + r.totalMemory());
		mem1 = r.freeMemory();
		System.out.println("Initial free memory: " + mem1);
		r.gc();
		mem1 = r.freeMemory();
		System.out.println("Free memory after garbage collection: " + mem1);

		List<xxx> someints = new ArrayList<>();

		for (int i = 0; i < 100000; i++)
			someints.add(new xxx("xx", i)); // allocate integers

		mem2 = r.freeMemory();
		System.out.println("Free memory after allocation: " + mem2);
		System.out.println("Memory used by allocation: " + (mem1 - mem2) / 1000
				+ " KB");
		// discard Integers

		r.gc(); // request garbage collection
		mem2 = r.freeMemory();
		System.out.println("Free memory after collecting"
				+ " discarded Integers: " + mem2);
	}

	public native void fromC();

	private static void fileReadWriteWork() {
		try (PrintWriter pw = new PrintWriter("pom2");) {

			Files.readAllLines(
					FileSystems.getDefault().getPath(".", "pom.xml"),
					Charset.defaultCharset()).stream().forEach(p -> {
				System.out.println(p);
				pw.write(p + "\n");
			});
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This method recursively lists all .txt and .java files in a directory
	 */
	private static void listRecursive(File dir) {
		Arrays.stream(
				dir.listFiles((f, n) -> !n.startsWith(".")
						&& (f.isDirectory() || n.endsWith(".txt") || n
								.endsWith(".java"))))

		.forEach(
				unchecked((file) -> {
					System.out.println(file.getCanonicalPath().substring(
							new File(".").getCanonicalPath().length()));

					if (file.isDirectory()) {
						listRecursive(file);
					}
				}));
	}

	/**
	 * This utility simply wraps a functional interface that throws a checked
	 * exception into a Java 8 Consumer
	 */
	private static <T> Consumer<T> unchecked(CheckedConsumer<T> consumer) {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	private interface CheckedConsumer<T> {
		void accept(T t) throws Exception;
	}

	@SuppressWarnings("unused")
	private static void annotationWork() {
		xxx x = new xxx("null", 0);
		@SuppressWarnings("unchecked")
		Class<xxx> cls = (Class<xxx>) x.getClass();

		try {
			Method m = cls.getMethod("join");

			System.out.println(m.getAnnotation(MyAnno.class));

		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void threadWork() {
		List<xxx> list = new ArrayList<>();
		for (int k = 0; k < 12; k++) {

			list.add(new xxx(k + "", k));
		}

		System.out.println(list.size());
		Stream<xxx> st = list.stream();

		List<xxx> newl = st.filter((p) -> {

			System.out.print(p.speed + "-");
			return p.speed < 50;

		}).collect(Collectors.toList());

		System.out.println(newl.size());

		for (xxx thread2 : list) {
			thread2.join();
		}
		System.out.println("here");
	}

	@MyAnno(str = "void", val = 100)
	public void join() {

	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {

			try {

				System.out.print(String.format("%c[%d;%df", ESC_CODE, this.no,
						i));

				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

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