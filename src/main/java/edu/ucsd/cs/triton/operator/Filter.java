package edu.ucsd.cs.triton.operator;

public interface Filter<T> {
	public boolean isKeep(T t);
}
