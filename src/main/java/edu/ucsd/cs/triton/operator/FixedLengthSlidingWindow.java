package operator;

public class FixedLengthSlidingWindow<T> implements SlidingWindow<T> {

	private int windowSize = 0;
	private T[] slidingWindow;
	private int headSlot = 0;
	private int tailSlot = 0;
	private int currentSize = 0;
	private BasicFilter<T> filter;
	
  public FixedLengthSlidingWindow(int windowSize) {
		initSlidingWindow(windowSize);
	}
	
	public FixedLengthSlidingWindow(int windowSize, BasicFilter<T> filter) {
		initSlidingWindow(windowSize);

		this.filter = filter;
	}
	
	@Override
  public void add(T element) {
	  // TODO Auto-generated method stub
		if (filter != null && filter.isKeep(element)) {
			if (currentSize == windowSize) {
				tailSlot = (tailSlot + 1) % windowSize;
			} else {
				currentSize++;
			}
			
		  this.slidingWindow[headSlot] = element;
			headSlot = (headSlot + 1) % windowSize;
		}
  }
	
	@Override
  public void remove(T element) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
	/*
	 * pos = 0 means current element, pos = 1 means the last 1st element
	 * @see operator.SlidingWindow#get(int)
	 */
  public T get(int pos) {
	  // TODO Auto-generated method stub
	  return this.slidingWindow[(headSlot - pos -1 + windowSize) % windowSize];
  }
	
	public int getWindowSize() {
		return this.windowSize;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		boolean isFirst = true;
		for (T t : this.slidingWindow) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(", ");
			}
			sb.append(t);
		}
		sb.append(']');
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
  private void initSlidingWindow(int windowSize) {
    if (windowSize <= 0) {
      throw new IllegalArgumentException("Number of slots must be greater than zero (you requested " + windowSize + ")");
    }
		this.windowSize = windowSize;
		slidingWindow = (T[]) new Object[this.windowSize];
	}
	
	/* test main */
	public static void main(String[] args) {

		FixedLengthSlidingWindow<Integer> slidingWindow = 
				new FixedLengthSlidingWindow<Integer> (5, new EvenFilter());
		
		for (int i = 0; i < 13; i++) {
			slidingWindow.add(i);
			System.out.println("add " + i + ": " + slidingWindow);
		}

	}
}

class EvenFilter extends BasicFilter<Integer> {
	@Override
  public boolean isKeep(Integer t) {
    // TODO Auto-generated method stub
    return (t % 2 == 0);
  }
}
