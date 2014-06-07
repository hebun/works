package freela.works;

import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.Random;

public class Gen implements Iterator<Integer>, Iterable<Integer> {
	int[] arr;
	int ind = 0;
	int size = 10;

	public Gen() {
		arr = new int[size];
		Random random = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = random.nextInt(100);
		}

	}

	@Override
	public boolean hasNext() {
		return ind < size;
	}

	@Override
	public Integer next() {

		return arr[ind++];
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

}
