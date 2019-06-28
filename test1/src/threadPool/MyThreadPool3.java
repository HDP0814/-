package threadPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;



/**
 * 限制最大长度的线程池，核心线程数可调整
 * 任务队列可手动设置固定的长度。
 * 工作流程：当任务放入线程池后，首先判断
 * 有没有核心线程，一开始线程池的线程数是
 * 0，所以会先建立线程，处理该任务。如果
 * 核心线程都在工作，则将任务存入任务队列
 * 中，若被任务队列填满后，线程池会建立临
 * 时线程处理任务，如果线程大于线程池的最
 * 大线程数，该 任务则被抛弃。临时线程当一
 * 段时间内接不到任务时将会自己销毁。
 * @author huangdongping
 *
 */
public class MyThreadPool3 {
	//记录运行状态
	private volatile int runState;
	static final int RUNNING    = 0;
	static final int SHUTDOWN   = 1;
	static final int STOP       = 2;
	static final int TERMINATED = 3;
	//线程池的主要状态锁，对线程池状态（比如线程池大小、runState等）的改变都要使用这个锁
	private final ReentrantLock mainLock = new ReentrantLock();   
	//线程池最大线程数量
	private volatile int maxMumPoolSize = 10000;
	//记录当前线程数量
	private volatile int poolSize = 0;
	//核心线程数量
	private volatile int corePoolSize = 10;
	//阻塞时的任务队列
	private LinkedBlockingQueue<Runnable> queue;
	//任务队列大小
	private int queueSize = 100;
	//工作线程集
	private HashSet<worker> workerSet;
	//线程工厂，用来创建线程
	//用来记录线程池中曾经出现过的最大线程数
	private int largestPoolSize = 0;
	//用来记录已经执行完毕的任务个数
	private volatile long completedTaskCount = 0;
	
	public long getCompletedTaskCount() {
		return completedTaskCount;
	}
	public void setCompletedTaskCount(long completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}
	public int getRunState() {
		return runState;
	}
	public void setRunState(int runState) {
		this.runState = runState;
	}
	public int getMaxMumPoolSize() {
		return maxMumPoolSize;
	}
	public void setMaxMumPoolSize(int maxMumPoolSize) {
		this.maxMumPoolSize = maxMumPoolSize;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
	public int getLargestPoolSize() {
		return largestPoolSize;
	}
	public void setLargestPoolSize(int largestPoolSize) {
		this.largestPoolSize = largestPoolSize;
	}

	private class worker extends Thread{
		boolean on = true;
		Runnable task = null;
		int i = 0;
		public worker(Runnable task){
			this.task = task;
		}
		public worker(){}
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println(isInterrupted());
				try {
					while(on && !isInterrupted()){
						if(task == null){
							task = queue.poll(2, TimeUnit.SECONDS);
						}
						if(task == null && poolSize > corePoolSize){
							poolSize--;
							return;
						}
						if(task != null){
							task.run();
							task = null;
							completedTaskCount++;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("线程池中断，停止等待");
				}	
		}
		
		public void cancle(){
//			this.interrupt();
			on = false;
		}
		
	}
	
	public MyThreadPool3(int corePoolSize,int queueSize){
		this.corePoolSize = corePoolSize;
		this.queueSize = queueSize;
		queue =  new LinkedBlockingQueue<Runnable>();
		workerSet = new HashSet<worker>();
		runState = RUNNING;
	}
	public MyThreadPool3(){
		queue =  new LinkedBlockingQueue<Runnable>();
		workerSet = new HashSet<worker>();
		runState = RUNNING;
	}
	
	public void execute(Runnable task){
		if( task == null ) throw new NullPointerException();
		if(runState != RUNNING)return;
		if(poolSize >= corePoolSize || !createWorker(task)){
			if(queue.size() >= queueSize || !queue.offer(task)){
				if(!createWorker(task)){
					task = null; // is shutdown or saturated
				}
			}
		}	
	}
	//创建核心线程和临时线程
	private boolean createWorker(Runnable task){
		boolean result;
		final ReentrantLock mainLock = this.mainLock;
		mainLock.lock();
		 try {
			 if(poolSize < maxMumPoolSize){
					worker workers = new worker(task);
					workerSet.add(workers);
					poolSize++;
					largestPoolSize++;
					workers.start();
					result =  true;
				}
				else{
					result =  false; 
				}
		 }finally{
			 mainLock.unlock();
		 }
		 return result;
	}
	//关闭线程池
	public void isShutDowm(){
		runState = SHUTDOWN;
		for(worker t:workerSet){
			t.cancle();
		}
	}

	//守护线程
//	public void daemonThread(){
//		new Thread("daemon"){
//			public void run(){
//				while(true){
//					if(!queue.isEmpty()){
//						queue.notifyAll();
//					}
//				}
//			}
//		}.start();
//	}
}
