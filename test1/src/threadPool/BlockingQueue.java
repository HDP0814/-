package threadPool;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

interface BlockingQueue<E> {
	boolean add(E e);
	//添加元素，添加成功返回true ,添加失败抛出异常 IllegalStateException。
	boolean offer(E e);
	//true:添加元素成功 ； false : 添加元素失败。
	void put(E e) throws InterruptedException;
	//添加元素，直到有空间添加成功才会返回，阻塞方法。
	boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;
	//true：添加数据成功，false:超时时间到。
	E take() throws InterruptedException;
	//获取队头的元素，阻塞方法，会一直等到有元素获取到才会返回，获取到元素时并将队列中的该元素删除。
	E poll(long timeout, TimeUnit unit) throws InterruptedException;
	//获取队头的元素，阻塞方法，超时时间到则返回null，获取到元素时并将队列中的该元素删除。
	int remainingCapacity();
	//返回理想情况下此队列可以添加的其他元素的数量.
	boolean remove(Object o);
	//移除指定的元素。
	boolean contains(Object o);
	//检查是否包含该元素
	int drainTo(Collection<? super E> c);
	//移除队列中的所有元素并添加到集合c，返回被移除元素的数量。
	int drainTo(Collection<? super E> c, int maxElements);
	//移除队列中maxElements个元素并添加到集合c，返回被移除元素的数量。

}
