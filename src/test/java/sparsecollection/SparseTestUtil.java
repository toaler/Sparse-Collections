package sparsecollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SparseTestUtil {
	
//	protected static int[] generateRandomSizedIntArrayWithRandomContent(int maxSize) {
//		final int size = (int) (Math.random() * maxSize);
//		int ints[] = new int[size];
//		return fillWithRandomInts(maxSize, ints);
//	}
//	
//	protected static int[] generateFixedSizedIntArrayWithRandomContent(int size) {
//		int ints[] = new int[size];
//		return fillWithRandomInts(size, ints);
//	}
//	
//	private static int[] fillWithRandomInts(int maxElementSize, int[] ints) {
//		for (int i = 0; i < ints.length; i++) {
//			ints[i] = (int) (Math.random() * maxElementSize);
//		}
//		return ints;
//	}
	
	protected static List<Integer> generateRandomSizedIntArrayWithRandomContentN(int maxSize) {
		return fillWithRandomIntsN((int) (Math.random() * maxSize), maxSize);
	}
	
	protected static List<Integer> generateFixedSizedIntArrayWithRandomContentN(int size) {
		return fillWithRandomIntsN(size, size);
	}
	
	private static List<Integer> fillWithRandomIntsN(int targetListSize, int maxElementSize) {
		ArrayList<Integer> list = new ArrayList<Integer>(targetListSize);
		
		for (int i = 0; i < targetListSize; i++) {
			list.add((int) (Math.random() * maxElementSize));
		}
		
		return list;
	}
}
