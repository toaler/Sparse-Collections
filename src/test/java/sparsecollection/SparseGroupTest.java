package sparsecollection;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

public class SparseGroupTest extends TestCase {
    
	public void testPopcountToPositionX() {

		long endTime = System.currentTimeMillis() + 100;

		while (System.currentTimeMillis() < endTime) {
			long bitmap = (long) (Long.MAX_VALUE * Math.random()) + 1;
			int index = findRandomIndexWithOneSet(bitmap);

			String bitStr = Long.toBinaryString(bitmap);
			int expectedCnt = 0;
			for (int i = bitStr.length() - index - 1; i < bitStr.length(); i++) {
				if (bitStr.charAt(i) == '1') {
					expectedCnt++;
				}
			}

			assertEquals(expectedCnt - 1, SparseGroup.popcount(bitmap, index));
		}
	}
	
	private int findRandomIndexWithOneSet(long bitmap) {
		int pos;
		
		do {
			pos = (int) (Math.random() * Long.SIZE);
		} while (!((bitmap & (1L << pos)) != 0));
		
		return pos;
	}

	public void testIsBitSet() {
		long endTime = System.currentTimeMillis() + 100;

		while (System.currentTimeMillis() < endTime) {
			Random rand = new Random();
			long bitmap = rand.nextLong();

			String bitStr = Long.toBinaryString(bitmap);

			for (int i = bitStr.length() - 1; i >= 0; i--) {
				char bit = bitStr.charAt(i);
				assertEquals(bit == '1',
						SparseGroup.isBitSet(bitmap, bitStr.length() - 1 - i));
			}
		}
	}
	
	public void testGet() {
		SparseGroup<Integer> ids = new SparseGroup<Integer>();

		ids.set(0, 0);
		assertEquals(new Integer(0), ids.get(0));
		assertEquals(1, ids.size());
		
		ids.set(1, 1);
		assertEquals(new Integer(0), ids.get(0));
		assertEquals(new Integer(1), ids.get(1));
		assertEquals(2, ids.size());
		
		ids.set(2, 2);
		assertEquals(new Integer(0), ids.get(0));
		assertEquals(new Integer(1), ids.get(1));
		assertEquals(new Integer(2), ids.get(2));
		assertEquals(3, ids.size());

	}
	
	public void testGet2() {
		SparseGroup<Integer> ids = new SparseGroup<Integer>();
		ids.set(2, 2);
		assertEquals(new Integer(2), ids.get(2));
		assertEquals(1, ids.size());
		
		ids.set(1, 1);
		assertEquals(new Integer(2), ids.get(2));
		assertEquals(new Integer(1), ids.get(1));
		assertEquals(2, ids.size());
		
		ids.set(0, 0);
		assertEquals(new Integer(2), ids.get(2));
		assertEquals(new Integer(1), ids.get(1));
		assertEquals(new Integer(0), ids.get(0));
		assertEquals(3, ids.size());
	}
	
	public void testGet3() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			// Create array of SparseGroup.MAX_SIZE elements, each containing a
			// random values from [0, N) where N is random int between 0..{@code
			// SparseGroup.MAX_SIZE}.
			List<Integer> list = SparseTestUtil.generateRandomSizedIntArrayWithRandomContentN(SparseGroup.MAX_SIZE);

			// Elements in SparseGroup buffer are added in random order, to test
			// copying of arbitrary ranges.
			SparseGroup<Integer> sg = new SparseGroup<Integer>();
			
			for (Integer i : list) {
				sg.set(i, i);
			}
			
			assertEquals((new HashSet<Integer>(list)).size(), sg.size());

			// Valdiate SparseGroup's elements are set correctly
	    	for (Integer i : list) {
				assertEquals(i, sg.get(i));
			}
		}
	}

	public void testRemove() {
		long endTime = System.currentTimeMillis() + 250;

		while (System.currentTimeMillis() < endTime) {
			// Create array of SparseGroup.MAX_SIZE elements, each containing a
			// random values from [0, N) where N is random int between 0..{@code
			// SparseGroup.MAX_SIZE}.
			List<Integer> list = SparseTestUtil.generateRandomSizedIntArrayWithRandomContentN(SparseGroup.MAX_SIZE);
			

			// Elements in SparseGroup buffer are added in random order.
			SparseGroup<Integer> sg = new SparseGroup<Integer>();
			for (Integer i : list) {
				sg.set(i, i);
			}

			Set<Integer> unique = new HashSet<Integer>(list);
			int remaining = unique.size();

			for (Integer i : unique) {
				sg.remove(i);
				remaining--;
				assertEquals(null, sg.get(i));
				assertEquals(remaining, sg.size());
			}
		}
	}
}
