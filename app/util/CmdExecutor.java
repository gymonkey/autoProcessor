package util;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import play.Logger;
import pojo.Command;

public class CmdExecutor implements Shutdownable{

	private final Executor executor;
	
	private final BlockingQueue<Command> cmdQueue;
	
	private final Thread thread;
	
	private volatile boolean isStarted;
	
	public CmdExecutor(){
		executor = new ThreadPoolExecutor(16, 32, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		cmdQueue = new LinkedBlockingQueue<Command>();
		thread = new Thread(new Runnable(){
			public void run(){
				while(isStarted){
					try{
						final Command cmd = cmdQueue.poll(1000, TimeUnit.SECONDS);
						if(cmd == null){
							continue;
						}else{
							executor.execute(new Runnable(){
								public void run(){
									try{
										String result = ServerConnector.runScriptOnServer(cmd.getHostName(), cmd.getCmd());
										cmd.setCmd(result);
										
										Logger.info("Run Script On %s Succeed", cmd.getHostName());
									}catch(Exception e){
										Logger.warn("Can't Connect to %s", cmd.getHostName());
									}finally{
										cmd.getLatch().countDown();
									}
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
	}
	
	public synchronized void start(){
		if(isStarted){
			return;
		}
		
		thread.start();
		isStarted = true;
	}
	
	public synchronized void close(){
		if(!isStarted){
			return;
		}else{
			isStarted = false;
			thread.interrupt();
		}
	}
	
	public void offer(Command cmd){
		if(!isStarted){
			cmdQueue.offer(cmd);
		}
	}
}
