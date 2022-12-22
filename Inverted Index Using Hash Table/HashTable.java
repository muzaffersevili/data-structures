import java.util.ArrayList;

public class HashTable<Key, Value> implements HashInterface<Key,Value> {

	private int capacity, size = 0, prime = 37, load_factor = 80;
	public long collision_num = 0;

	private String alphabet = " abcdefghijklmnopqrstuvwxyz";
	private ArrayList<Node<Key, Value>> table;

	public HashTable(int capacity) {
		this.capacity = capacity;
		table = new ArrayList<Node<Key, Value>>(capacity);

		for (int i = 0; i < capacity; i++)
			table.add(null);
	}

	private int Simple_Summation_Function(Key k) {

		String key = k.toString();
		int total = 0;

		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int index = alphabet.indexOf(c);
			total += index;
		}

		return total % (capacity - 1);
	}

	private int Polynomial_Accumulation_Function(Key k) {

		String key = k.toString();
		int length = key.length() - 1;
		int total = 0;

		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int index = alphabet.indexOf(c);
			int value = Math.abs((int) (index * Math.pow(prime, length - i)));
			total += value;
		}

		return total % (capacity - 1);
	}

	private int DoubleHashing(Key k) {
		int code = Simple_Summation_Function(k);
		//int code = Polynomial_Accumulation_Function(k);
		int d = 13 - (code % 13);
		int i = 1;
		int doublehashing = code;

		if(code>=0) {
			Node<Key, Value> v = table.get(code);
			
			while (v != null) {
				
				if (!v.getKey().equals(k)) {
					collision_num++;
					doublehashing = (code + i * d) % capacity;
					v = table.get(doublehashing);
					break;
				}
				i++;
				if (i >= this.capacity)
					break;
			}
		}
		return doublehashing;
	}

	public void put(Key K, Value V) {
		//int index = Simple_Summation_Function(K);
		//int index = Polynomial_Accumulation_Function(K);
		int index = DoubleHashing(K);
		
		if (index >= 0 && capacity >= 0) {
			Node<Key, Value> addToData = new Node<Key, Value>(K, V);
			
			// Linear Probing
			while (table.get(index) != null) {
				if (!table.get(index).getKey().equals(K)) {
					index++;
					collision_num++;
					if (index >= capacity) {
						index = 0;
					}
				}
				else 
					break;
			}

			table.set(index, addToData);
			size++;

			int limit = capacity * load_factor / 100;
			if (size >= limit) {
				resize();
			}
		}
	}

	public Value get(Key K) {
		// int index = Simple_Summation_Function(K);
		int index = Polynomial_Accumulation_Function(K);
		// int index = DoubleHashing(K);
		
		if (index >= 0 && capacity >= 0) {
			// Linear Probing
			while (table.get(index) != null) {
				
				if (!table.get(index).getKey().equals(K)) {
					index++;

					if (index >= capacity) 
						index = 0;
				} 
				else
					break;
			}

			if (table.get(index) != null)
				return table.get(index).getValue();
			else
				return null;
		} 
		else
			return null;
	}

	public void resize() {
		ArrayList<Node<Key, Value>> temp = table;
		capacity = capacity * 2 - 1;
		table = new ArrayList<Node<Key, Value>>(capacity);

		for (int i = 0; i < capacity; i++) {
			table.add(null);
		}

		size = 0;
		for (Node<Key, Value> i : temp) {
			if (i != null)
				put(i.getKey(), i.getValue());
		}
	}

	public void remove(Key K) {
		// int index = Simple_Summation_Function(K);
		int index = Polynomial_Accumulation_Function(K);
		// int index = DoubleHashing(K);
		
		// Linear Probing
		while (table.get(index) != null) {

			if (!table.get(index).getKey().equals(K)) {
				index++;
				
				if (index >= capacity) 
					index = 0;
			} 
			else 
				break;
		}
		table.set(index, null);
		size--;
	}
	
	public void collision() {
		System.out.print("Collision: "+collision_num);
	}
	
}
