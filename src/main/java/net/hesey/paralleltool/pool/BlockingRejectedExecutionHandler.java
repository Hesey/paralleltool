package net.hesey.paralleltool.pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * RejectedExecutionHandler with Blocking Submit
 * 
 * @author Hesey
 * 
 */
public class BlockingRejectedExecutionHandler implements RejectedExecutionHandler {
	/**
	 * When submit a task, if the task queue is full. The submit task will be
	 * hold until the queue is free.
	 */
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		if (!executor.isShutdown()) {
			try {
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
			}
		}
	}
}
