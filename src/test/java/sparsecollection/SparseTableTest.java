package sparsecollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class SparseTableTest extends TestCase {
	public void testGet() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> list = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);

			// Elements intended for SparseTable are added in random order
			SparseTable<Integer> st = new SparseTable<Integer>(list.size());
			for (Integer i : list) {
				st.set(i, i);
			}
			
			// Count uniq elements
			Set<Integer> uniq = new HashSet<Integer>(list);
			assertEquals(uniq.size(), st.size());
			
			// Validate SparseTable's elements are set correctly
			for (Integer i : list) {
				assertEquals(i, st.get(i));
			}
		}
	}
	
	public void testRemove() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			List<Integer> list = SparseTestUtil.generateFixedSizedIntArrayWithRandomContentN(100);
			
			// Elements intended for SparseTable are added in random order
			SparseTable<Integer> st = new SparseTable<Integer>(list.size());
			for (Integer i : list) {
				st.set(i, i);
			}
			
			// Dedupe elements.
			Set<Integer> uniq = new HashSet<Integer>(list);
			assertEquals(uniq.size(), st.size());
			
			// Valdiate SparseTable's elements are set correctly
			for (Integer i : list) {
				assertEquals(i, st.get(i));
			}

			// Valdiate old value returned from remove is correct.
			for (Integer i : uniq) {
				assertEquals(i, st.remove(i));
			}
			assertTrue(st.isEmpty());
		}
	}
	
	public void testIsEmpty() {
		SparseTable<Integer> st = new SparseTable<Integer>(0);
		assertTrue(st.isEmpty());
		
		st.set(0, 0);
		assertFalse(st.isEmpty());
	}
}
