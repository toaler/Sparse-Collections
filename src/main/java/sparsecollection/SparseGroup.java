package sparsecollection;

/**
 * A {@code SparseGroup} is a space efficient linear data structure (vector),
 * which uses a bitmap of size {@code MAX_SIZE} to indicate if a particular
 * index in the vector is assigned (1) or not (0). For a lookup on i, the i'th
 * entry in the bitmap is checked, and if it's set then a popcount on
 * bitmap[0..i-1] is evaluated to work out the position of the {@code value} in
 * the corresponding vector.
 * 
 * A {@code SparseGroup} uses minimal amount of memory to store unassigned slots
 * in the vector.  The overhead is 1-bit per unassigned slot.
 * 
 * 
 * @author btoal
 * 
 * @param <V>
 */
public class SparseGroup<V> {
	protected static final int MAX_SIZE = 48;
	
	private Object[] buf = new Object[0];
	private long posBits = 0;
	
    private static final byte[] BITS_IN = {
      0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4,
      1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
      1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
      1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
      2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
      3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
      4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
    };
    
	public boolean set(int index, V value) {
		int i = popcount(posBits, index);
		
		boolean newElement = false;
		if (!isBitSet(posBits, index)) {
			Object[] tmpbuf = new Object[buf.length + 1];
			
			System.arraycopy(buf, 0, tmpbuf, 0, i);
			System.arraycopy(buf, i, tmpbuf, i + 1, buf.length - i);
			buf = tmpbuf;
			
			setBit(index);
			newElement = true;
		} 

//		System.out.println("Key = " + key + " value = " + value);
//		System.out.println(Long.toBinaryString(posBits));
//		System.out.println(Arrays.toString(buf));
//		System.out.println("i = " + i + " size = " + buf.length);
		buf[i] = value;
		return newElement;
	}
	
	public V remove(int index) {
		V oldValue = null;
		
		int i = popcount(posBits, index);

		if (isBitSet(posBits, index)) {
			System.out.println("Deleted");
			oldValue = (V) buf[popcount(posBits, index)];
			
			Object[] tmpbuf = new Object[buf.length - 1];
			if (tmpbuf.length > 0) {
				System.arraycopy(buf, 0, tmpbuf, 0, i);
				System.arraycopy(buf, i + 1, tmpbuf, i, buf.length - i - 1);
			}
			
			buf = tmpbuf;

			unsetBit(index);
		}
		
		return oldValue;
	}
    
	public V get(int b) {
		if (isBitSet(posBits, b)) {
			return (V) buf[popcount(posBits, b)];
		} else {
			return null;
		}
	}
	
	protected V getDirect(int i) {
		return (V) buf[i];
	}

	private void setBit(int pos) {
		posBits = posBits | (1L << pos);
	}
	
	private void unsetBit(int pos) {
		posBits = posBits & ~(1L << pos);
	}

	/**
	 * Given a bitmap and a desired position, return the popcount for
	 * bitmap[0..pos-1].
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Hamming_weight">Hamming
	 *      weight</a> for more info.
	 * 
	 * @param bitmap
	 *            that contains up to {@code M} elements, which is used to mark
	 *            which indexes are assigned. A bit set to 0 and 1 represent a
	 *            unassigned and assigned index respectively.
	 * @param pos
	 *            position of{@code bitmap} starting at zero.
	 * @return count of ones.
	 */
	protected static int popcount(long bitmap, int pos) {
		int cnt = 0, shift = 0, rem = pos, chunk;
		
		for (; rem > 8; rem -= 8, shift += 8) {
			chunk = (int)((bitmap >> shift) & 0xFF);
			cnt += BITS_IN[chunk];
		}
		
		chunk = (int) ((bitmap >> shift) & ((1L << rem) - 1));
		return cnt + BITS_IN[chunk];
	}

	// Test if entry is part of bucket.  Shift out bit at position
	protected static boolean isBitSet(long bitmap, int b) { 
		return (bitmap & (1L << b)) != 0; 
	}

	public int size() {
		return buf.length;
	}
}