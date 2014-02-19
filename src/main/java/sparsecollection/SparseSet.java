package sparsecollection;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

public class SparseSet<V> extends AbstractSet<V> implements Set<V> {
	private static final int DEFAULT_SIZE = 32;
	private final SparseTable<V> st;

	public SparseSet() {
		this(DEFAULT_SIZE);
	}

	public SparseSet(int length) {
		st = new SparseTable<V>(length);
	}

	@Override
	public int size() {
		return st.size();
	}

	@Override
	public boolean isEmpty() {
		return st.size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		int hash = o.hashCode();
		int i = 0;
		V value;
		
		while (1 == 1) {
			int triangularNum = ((i * (i + 1)) / 2);
//			System.out.println(hash + triangularNum);
			value = st.get(hash + triangularNum);
			
//			System.out.println("Add " + hash);
			if (value == null) {
				return false;
			} else if (value.equals((V) o)) {
				return true;
			}
		}
	}


	@Override
	public boolean add(Object e) {
		int hash = e.hashCode();
		int i = 0;
		V value;
		
		while (1 == 1) {
			int triangularNum = ((i * (i + 1)) / 2);
			//System.out.println(hash + triangularNum);
			value = st.get(hash + triangularNum);
			
			//System.out.println("Add " + hash);
			if (value == null) {
				// slot emtpy
				st.set(hash, (V) e);
				return true;
			} else if (value.equals((V) e)) {
				st.set(hash, (V) e);
				return false;
			}
		}
	}

	@Override
	public boolean remove(Object e) {
		int hash = e.hashCode();
		int i = 0;
		V value;
		
		while (1 == 1) {
			int triangularNum = ((i * (i + 1)) / 2);
//			System.out.println(hash + triangularNum);
			value = st.get(hash + triangularNum);
			
//			System.out.println("Add " + hash);
			if (value == null) {
				return false;
			} else if (value.equals((V) e)) {
				st.remove(hash);
				return true;
			}
		}
	}

	@Override
	public Iterator<V> iterator() {
		return new SparseSetIterator<V>(st);
	}
	
	private static class SparseSetIterator<V>  implements Iterator<V> {
		private final SparseTable<V> st;
		private final int elements;
		
		private int count;
		private int sparseTableSize;
		private int position;
		private int lastPosition;


		public SparseSetIterator(SparseTable<V> st) {
			this.st = st;
			position = 0;
			elements = st.size();
			sparseTableSize = st.maxSize();
		}

		@Override
		public boolean hasNext() {
			return count < elements;
		}

		@Override
		public V next() {
			V value = null;
			lastPosition = position;
			while (position < sparseTableSize) {
				value = st.get(position++);
				if (value != null) {
					count++;
					break;
				}
			}
			
			return value;
		}

		@Override
		public void remove() {
			st.remove(lastPosition);
		}
	}
}
