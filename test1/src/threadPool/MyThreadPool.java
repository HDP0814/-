package threadPool;

import java.util.LinkedList;
import java.util.List;
/**
 * 创建自己的线程池
 * @author huangdongping
 *
 */
public class MyThreadPool {
	//线程池中允许的最大线程数
	private static int MAXTHREADNUM = 10000;
	//核心线程数
	private int threadNum;
	//阻塞任务队列
	private List<Runnable> waitQueue;
	
	private WorkerThread[] workerThreads;
	
	private class WorkerThread extends Thread {
		String lock = "lock";
	    private volatile boolean on = true;
	    @Override
	    public void run() {
	        Runnable task = null;
	        System.out.println(isInterrupted());
	        while(on&&!isInterrupted()){
			    synchronized (lock){
	                    while (on && !isInterrupted() && waitQueue.isEmpty()) {
	                        //这里如果使用阻塞队列来获取在执行时就不会报错
	                        //报错是因为退出时销毁了所有的线程资源，不影响使用
	                    }
			        if (on && !isInterrupted() && !waitQueue.isEmpty()) {
			            task = waitQueue.remove(0);
			        }

			        if(task !=null){
			            //取到任务后执行
			            task.run();
			        }
			    }
			}
	        task = null;//任务结束后手动置空，加速回收
	    }

	    public void cancel(){
	        on = false;
	        interrupt();
	    }
	}
	
	public MyThreadPool(int threadNum) {
        this.threadNum = threadNum;
        if(threadNum > MAXTHREADNUM)
            threadNum = MAXTHREADNUM;
        this.waitQueue = new LinkedList<>();
        this.workerThreads = new WorkerThread[threadNum];
        init();
    }

    //初始化线程池中的线程
	private void init(){
		for(int i=0;i<threadNum;i++){
			workerThreads[i] = new WorkerThread();
			workerThreads[i].start();
		}
	}
	
	 //提交任务
    public void execute(Runnable task){
        synchronized (waitQueue){
        	waitQueue.add(task);
            //提交任务后唤醒等待在队列的线程
        	waitQueue.notifyAll();
        }
    }
    
    //销毁线程池
    public void shutdown(){
        for(int i=0;i<threadNum;i++){
            workerThreads[i].cancel();
            workerThreads[i] = null;
        }
        waitQueue.clear();
    }
}
