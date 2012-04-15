package util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import models.Fault;

public class FaultManager implements Shutdownable{

	private final BlockingQueue<Fault> faultQueue;
	
	private final Executor executor;
	
	private final Thread thread;
	
	private volatile boolean isStarted;
	
	public FaultManager(){
		faultQueue = new LinkedBlockingQueue<Fault>();
		executor = new ThreadPoolExecutor(32, 64, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		thread = new Thread(new Runnable(){
			public void run(){
				while(isStarted){
					try{
						Fault fault = faultQueue.poll(1000, TimeUnit.SECONDS);
						if(fault == null){
							continue;
						}else{
							executor.execute(new Runnable(){
								public void run(){
									
								}
							});
						}
					}catch(InterruptedException e){
						//do nothing but break the loop
						break;
					}
				}
			}
		});
		isStarted = false;
	}
	
	public synchronized void start(){
		if(isStarted){
			return;
		}
		
		isStarted = true;
		thread.start();
	}
	
	public void offer(Fault fault){
		if(!isStarted){
			return;
		}
		faultQueue.offer(fault);
	}
	
	public synchronized void close(){
		if(!isStarted){
			return;
		}
		
		isStarted = false;
		thread.interrupt();
	}
}
