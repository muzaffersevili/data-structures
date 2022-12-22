
public interface HashInterface<Key, Value> {
	
	public void put(Key k,Value v);
	
	public Value get(Key k);
	
	public void resize();
	
	public void remove(Key k);
}
