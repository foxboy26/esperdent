package operator;

public interface Filter<T> {
	public boolean isKeep(T t);
}
