package net.hesey.paralleltool.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The base ThreadPool.
 * 
 * @author Hesey
 * 
 */
public class BlockingThreadPool {
	public static final int DEFAULT_THREAD_NUM = 30;
	public static final int DEFAULT_QUEUE_CAPACITY = 1000;

	public static ExecutorService newInstance() {
		return newInstance(DEFAULT_THREAD_NUM);
	}

	public static ExecutorService newInstance(int threadNum) {
		return newInstance(threadNum, DEFAULT_QUEUE_CAPACITY);
	}

	public static ExecutorService newInstance(int threadNum, int queueCapacity) {
		return new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueCapacity), new BlockingRejectedExecutionHandler());
	}
}
