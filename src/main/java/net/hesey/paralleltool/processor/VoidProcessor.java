package net.hesey.paralleltool.processor;

/**
 * Processor without Callback.
 * 
 * If you need no result. Use this processor.
 * 
 * @author Hesey
 * 
 */
public interface VoidProcessor<E> {
	public void process(E e);
}
