package threadPool;

import java.util.concurrent.CountDownLatch;

public class ThreadPoolTest {
	private static volatile int num = 0 ;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		MyThreadPool t = new MyThreadPool(10);
		MyThreadPool2 t2 = new MyThreadPool2(10);
//		CountDownLatch countDownLatch = new CountDownLatch(5);
		for (int i = 0; i < 1000; i++) {
			t2.execute(new Task());
		}
		
//		t.shutdown();
	}

	static class Task implements Runnable{
		
		@Override
		public void run() {
			num++;
			// TODO Auto-generated method stub
			System.out.println("当前线程:"+Thread.currentThread().getName()+" num:"+num);
		}
		
	}
}
