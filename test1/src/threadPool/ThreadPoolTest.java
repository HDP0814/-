package threadPool;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	private static volatile int num = 0 ;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
//		MyThreadPool t = new MyThreadPool(10);
		MyThreadPool3 t2 = new MyThreadPool3(10,100);
		Date time1 = new Date();
//		CountDownLatch countDownLatch = new CountDownLatch(5);
		for (int i = 0; i < 100; i++) {
			t2.execute(new Task());
			System.out.println("核心线程数:"+t2.getCorePoolSize());
			System.out.println("线程的总数:"+t2.getPoolSize());
		}
//		Thread.sleep(10000);
//		System.out.println("10秒后的线程数量:"+t2.getPoolSize());
		long k = t2.getCompletedTaskCount();
		while(t2.getCompletedTaskCount() != 100);
		t2.isShutDowm();
		System.out.println("完成的任务数:"+t2.getCompletedTaskCount());
		Date time2 = new Date();
		System.out.println("总耗时:"+(time2.getTime()-time1.getTime())+"ms");
	}
}

class Task implements Runnable{
	
	@Override
	public void run() {
		System.out.println("当前线程:"+Thread.currentThread().getName()+"正在执行task ");
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		// TODO Auto-generated method stub
		System.out.println("当前线程:"+Thread.currentThread().getName()+" task执行完毕");
	}
	
}