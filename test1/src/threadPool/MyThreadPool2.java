package threadPool;

import java.util.ArrayList;
import java.util.List;

public class MyThreadPool2 {
	//线程能开启的最大个数
//	private final int THREADNUMBERMAX = 10000;
	//记录线程数量
//	private int countThread = 0;
	//核心线程数量
	private int coreThreadNumber;
	//阻塞时的任务队列
	private List<Runnable> queue;
	//核心线程工作组
	private workerThread[] worker;

	
	private class workerThread extends Thread{
		boolean on = true;
		Runnable task = null;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(on){
				synchronized(queue){
					while(on && !isInterrupted() && queue.isEmpty()){
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(on && !isInterrupted() && !queue.isEmpty()){
						task = queue.remove(0);
					}
				}
				if(task != null){
					task.run();
					task = null;
				}
			}
		}
		
		public void cancle(){
			this.interrupt();
			on = false;
		}
		
	}
	
	public MyThreadPool2(int coreThreadNumber){
		this.coreThreadNumber = coreThreadNumber;
		queue = new ArrayList<Runnable>();
		worker = new workerThread[coreThreadNumber];
		for(int i = 0 ; i < coreThreadNumber ; i++){
			worker[i] = new workerThread();
			worker[i].start();
		}
		daemonThread();//开启守护线程
	}
	
	public void execute(Runnable task){
		if(task != null){
			queue.add(task);
		}
	}
	
	public void isShutDowm(){
		for(int i = 0 ; i < coreThreadNumber; i++){
			worker[i].cancle();
		}
	}
	
	//
	public void temporaryTask(Runnable task){
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				super.run();
				Runnable task = null;
				int flag = 0;
				synchronized(queue){
					while(true){
						if(queue.isEmpty()){
							try {
								queue.wait(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							flag++;
							if(flag == 5)break;
						}
						else{
							task = queue.remove(0);
						}
						if(task != null){
							task.run();
							task = null;
						}
					}
				}
			}
			
		}.start();
	}
	
	//守护线程
	public void daemonThread(){
		new Thread("daemon"){
			public void run(){
				while(true){
					if(!queue.isEmpty()){
						queue.notifyAll();
					}
				}
			}
		}.start();
	}
}
