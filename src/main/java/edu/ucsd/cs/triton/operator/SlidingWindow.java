package edu.ucsd.cs.triton.operator;

public interface SlidingWindow<T> {
	public void add(T element);
	public void remove(T element);
	public T get(int pos);
	public int count();
	
}
