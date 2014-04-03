package net.hesey.paralleltool;

import java.util.List;

import com.google.common.collect.Lists;

public class Test {
	public static void main(String[] args) {
		List<Integer> list = Lists.newArrayList(65, 66, 67, 68, 69, 70);

		ListParallel.parallel(list, new Processor<Integer>() {
			@Override
			public void process(Integer e) {
				System.out.println(e + 1);
			}
		});

		List<Character> result = ListParallel.parallelWithCallback(list, new CallbackProcessor<Integer, Character>() {
			@Override
			public Character process(Integer e) {
				return (char) e.intValue();
			}
		});
		System.out.println(result);
	}
}
