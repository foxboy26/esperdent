package operator;

public abstract class BasicFilter<T> implements Filter<T> {
	public boolean isKeep(T t) {
		return true;
	}
}
