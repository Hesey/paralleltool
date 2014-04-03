package net.hesey.paralleltool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.hesey.paralleltool.pool.BlockingThreadPool;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ListParallel {
	public static <E> void parallel(final List<E> list, final Processor<E> processor) {
		List<E> copyList = ImmutableList.copyOf(list);
		ExecutorService exec = BlockingThreadPool.newInstance();
		for (final E object : copyList) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					processor.process(object);
				}
			});
		}
		exec.shutdown();
	}

	public static <F, T> List<T> parallelWithCallback(final List<F> list, final CallbackProcessor<F, T> processor) {
		List<F> copyList = ImmutableList.copyOf(list);
		List<Future<T>> futureList = Lists.newArrayListWithExpectedSize(list.size());
		List<T> resultList = Lists.newArrayListWithExpectedSize(list.size());
		ExecutorService exec = BlockingThreadPool.newInstance();
		for (final F object : copyList) {
			futureList.add(exec.submit(new Callable<T>() {
				@Override
				public T call() throws Exception {
					return processor.process(object);
				}
			}));
		}
		for (Future<T> future : futureList) {
			try {
				resultList.add(future.get());
			} catch (Exception e) {
			}
		}
		exec.shutdown();
		return resultList;
	}
}
