package net.hesey.paralleltool;

/**
 * Processor with Callback.
 * 
 * If you need a result to return, use this Processor.
 * 
 * @author Hesey
 * 
 */
public interface CallbackProcessor<F, T> {
	public T process(F e);
}
