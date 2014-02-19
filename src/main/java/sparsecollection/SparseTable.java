package sparsecollection;

public class SparseTable<V> {
	private Object[] groups; // Is a unchecked SparseGroup<V>[]
	private int elements;

	public SparseTable(int size) {
		elements = 0;
		groups = new Object[(size/SparseGroup.MAX_SIZE) + 1];
		
		for (int i = 0; i < groups.length ; i++) {
			groups[i] = new SparseGroup<V>();
		}
//		System.out.println("created st for size = " + size + " groups = " + groups.length);
	}
	
	public int numberOfGroups() {
		return groups.length;
	}

	public void set(int index, V value) {
//		System.out.println("set(" + index + ", " + value + ")");
		if (getGroup(index).set(getPosition(index), value)) {
			elements++;
		}
	}

	protected SparseGroup<V> getGroup(int index) {
		return (SparseGroup<V>) groups[index/SparseGroup.MAX_SIZE];
	}
	
//	protected SparseGroup<V> getGroupDirect(int group) {
//		return (SparseGroup<V>) groups[group];
//	}

	private int getPosition(int index) {
		return index % SparseGroup.MAX_SIZE;
	}

	public V get(int index) {
		return (V) getGroup(index).get(getPosition(index));
	}

	public int size() {
		return elements;
	}

	public boolean isEmpty() {
		return elements == 0;
	}

	public V remove(int index) {
		SparseGroup<V> group = getGroup(index);
		V oldValue = group.remove(getPosition(index));
		elements--;
		
		return oldValue;
	}
	
	public int maxSize() {
		return groups.length * SparseGroup.MAX_SIZE;
	}
	
}