package sparsecollection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class SparseSetTest extends TestCase {

	public void testAdd() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> ints = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(ints.size());
//			System.out.println(Arrays.toString(ints));
			for (int i = 0; i < ints.size(); i++) {
				ss.add(ints.get(i));
			}
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>();
			for (int i = 0; i < ints.size(); i++) {
				uniq.add(ints.get(i));
			}

			assertEquals(uniq.size(), ss.size());
			// Valdiate SparseTable's elements are set correctly
			for (int i = 0; i < ints.size(); i++) {
				assertEquals(uniq.contains(ints.get(i)), ss.contains(ints.get(i)));
			}
		}
	}
	
	public int testSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void testIsEmpty() {
		Set<Integer> ss = new SparseSet<Integer>();
		assertEquals(true, ss.isEmpty());
		
		ss.add(1);
		assertEquals(false, ss.isEmpty());
		
		ss.remove(1);
		assertEquals(true, ss.isEmpty());
    }

	public void testContains() {
		Set<Integer> ss = new SparseSet<Integer>();
		assertEquals(false, ss.contains(1));
		
		ss.add(1);
		assertEquals(true, ss.contains(1));
		
		ss.remove(1);
		assertEquals(false, ss.contains(1));
	}

	public void testIterator() {
		List<Integer> list = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

		// TODO : SparseSet should be able to take any collection.
		// Elements intended for SparseSet are added in random order
		Set<Integer> ss = new SparseSet<Integer>(list.size());
		for (Integer i : list) {
			ss.add(i);
		}
		
		// Count uniq elements
		Set<Integer> uniq = new HashSet<Integer>(list);
		assertEquals(uniq.size(), ss.size());
		System.out.println("size = " + uniq.size() + " list " + list.size());
		
		for (Integer i : ss) {
			System.out.println("Blah Removing " + i);
			uniq.remove(i);
		}
		
		assertEquals(0, uniq.size());
		
	}

	public void testToArray1() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> list = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(list.size());
			System.out.println(list);
			for (Integer i : list) {
				ss.add(i);
			}
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>(list);
			assertEquals(uniq.size(), ss.size());
			
			Object[] actual = ss.toArray();
			assertEquals(uniq.size(), actual.length);
			
//			System.out.println(actual.length);
			
			for (Object i : actual) {
//				System.out.println("blah = " + (Integer) i);
				assertTrue(uniq.contains((Integer) i));
			}
		}	
	}

	public void testToArray2() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> list = SparseTestUtil
					.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(list.size());
//			System.out.println(list);
			for (Integer i : list) {
				ss.add(i);
			}

			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>(list);
			assertEquals(uniq.size(), ss.size());

			Integer[] actual = new Integer[ss.size()];
			ss.toArray(actual);

			assertEquals(uniq.size(), actual.length);

//			System.out.println(actual.length);

			for (Integer i : actual) {
//				System.out.println("blah = " + (Integer) i);
				assertTrue(uniq.contains((Integer) i));
			}
		}
	}

	public void testRemove() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> ints = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(ints.size());
//			System.out.println(Arrays.toString(ints));
			for (int i = 0; i < ints.size(); i++) {
				ss.add(ints.get(i));
			}
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>();
			for (int i = 0; i < ints.size(); i++) {
				uniq.add(ints.get(i));
			}

			assertEquals(uniq.size(), ss.size());
			for (int i : uniq) {
				assertTrue(ss.remove(i));
			}
			assertEquals(0, ss.size());
		}
	}

	public void testContainsAll() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			Set<Integer> ints = new HashSet<Integer>(
					SparseTestUtil
							.generateFixedSizedIntArrayWithRandomContentN(100));

			// Elements intended for SparseSet are added in random order
			// TODO : Build SparseSet constructor that takes collection
			// TODO : Add resize functionality to SparseSet
			Set<Integer> ss = new SparseSet<Integer>(100);
			for (Integer i : ints) {
				ss.add(i);
			}

			assertEquals(ints.size(), ss.size());
			assertTrue(ss.containsAll(ints));
		}
	}
	
	public void testAddAll() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> ints = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(ints.size());
			ss.addAll(ints);
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>(ints);
			assertEquals(uniq.size(), ss.size());
			
			assertEquals(uniq, ss);
		}
	}

	public void testRetainAll() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			Set<Integer> ints = new HashSet<Integer>(
					SparseTestUtil
							.generateFixedSizedIntArrayWithRandomContentN(100));

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(100);
			for (Integer i : ints) {
				ss.add(i);
			}

			assertEquals(ints.size(), ss.size());
			assertFalse(ss.retainAll(ints));
			assertEquals(ints.size(), ss.size());
			assertTrue(ss.retainAll(Collections.EMPTY_SET));
			assertEquals(0, ss.size());
		}
	}

	public void testRemoveAll() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> ints = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(ints.size());
			ss.addAll(ints);
			Set<Integer> uniq = new HashSet<Integer>(ints);
			assertEquals(uniq.size(), ss.size());
			
			assertTrue(ss.removeAll(uniq));
			assertEquals(0, ss.size());
		}
	}

	public void testClear() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> ints = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseSet are added in random order
			Set<Integer> ss = new SparseSet<Integer>(ints.size());
			ss.addAll(ints);
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>(ints);
			assertEquals(uniq.size(), ss.size());
			
			ss.clear();
			assertEquals(0, ss.size());
		}
	}
}
