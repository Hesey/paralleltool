package net.hesey.paralleltool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.hesey.paralleltool.pool.BlockingThreadPool;
import net.hesey.paralleltool.processor.CallbackProcessor;
import net.hesey.paralleltool.processor.VoidProcessor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ListParallel {
	public static <E> void parallel(final List<E> list, final VoidProcessor<E> processor) {
		ExecutorService exec = BlockingThreadPool.newInstance();
		execute(list, processor, exec);
	}

	public static <E> void parallel(final List<E> list, final VoidProcessor<E> processor, int threadNum) {
		ExecutorService exec = BlockingThreadPool.newInstance(threadNum);
		execute(list, processor, exec);
	}

	public static <F, T> List<T> parallelWithCallback(final List<F> list, final CallbackProcessor<F, T> processor) {
		ExecutorService exec = BlockingThreadPool.newInstance();
		return executeWithCallback(list, processor, exec);
	}

	public static <F, T> List<T> parallelWithCallback(final List<F> list, final CallbackProcessor<F, T> processor, int threadNum) {
		ExecutorService exec = BlockingThreadPool.newInstance(threadNum);
		return executeWithCallback(list, processor, exec);
	}

	private static <F, T> List<T> executeWithCallback(final List<F> list, final CallbackProcessor<F, T> processor, ExecutorService exec) {
		List<F> copyList = ImmutableList.copyOf(list);
		List<Future<T>> futureList = submitFuture(processor, exec, copyList);
		List<T> resultList = getFutureResult(futureList);
		exec.shutdown();
		return resultList;
	}

	private static <T> List<T> getFutureResult(List<Future<T>> futureList) {
		List<T> resultList = Lists.newArrayListWithExpectedSize(futureList.size());
		for (Future<T> future : futureList) {
			try {
				resultList.add(future.get());
			} catch (Exception e) {
			}
		}
		return resultList;
	}

	private static <T, F> List<Future<T>> submitFuture(final CallbackProcessor<F, T> processor, ExecutorService exec, List<F> copyList) {
		List<Future<T>> futureList = Lists.newArrayListWithExpectedSize(copyList.size());
		for (final F object : copyList) {
			futureList.add(exec.submit(new Callable<T>() {
				@Override
				public T call() throws Exception {
					return processor.process(object);
				}
			}));
		}
		return futureList;
	}

	private static <E> void execute(final List<E> list, final VoidProcessor<E> processor, ExecutorService exec) {
		List<E> copyList = ImmutableList.copyOf(list);
		submitTask(processor, exec, copyList);
		exec.shutdown();
	}

	private static <E> void submitTask(final VoidProcessor<E> processor, ExecutorService exec, List<E> copyList) {
		for (final E object : copyList) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					processor.process(object);
				}
			});
		}
	}
}
