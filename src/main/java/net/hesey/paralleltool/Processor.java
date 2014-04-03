package net.hesey.paralleltool;

/**
 * Processor without Callback.
 * 
 * If you need no result. Use this processor.
 * 
 * @author Hesey
 * 
 */
public interface Processor<E> {
	public void process(E e);
}
