package net.hesey.paralleltool.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The base ThreadPool.
 * 
 * @author Hesey
 * 
 */
public class BlockingThreadPool {
	public static int THREAD_NUM = 30;
	public static int QUEUE_CAPACITY = 1000;

	public static ThreadPoolExecutor newInstance() {
		return new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY), new BlockingRejectedExecutionHandler());
	}
}
