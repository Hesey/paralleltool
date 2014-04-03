## Parallel Tool ##

A simple and optimized tool to parallel tasks.

Use this tool like `Collection.parallelStream` in Java 8;

For now it supports to process `List`.

- Process without Result return
```java
List<Integer> list = Lists.newArrayList(65, 66, 67, 68, 69, 70);
ListParallel.parallel(list, new VoidProcessor<Integer>() {
	@Override
	public void process(Integer e) {
		System.out.println(e + 1);
	}
});
```
- Process with Result return
```java
List<Character> result = ListParallel.parallelWithCallback(list, new CallbackProcessor<Integer, Character>() {
	@Override
	public Character process(Integer e) {
		return (char) e.intValue();
	}
});
```