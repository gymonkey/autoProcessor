package util;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;

import type.FaultType;
import type.JobType;
import util.chain.DetectChain;
import util.chain.DetectContext;
import util.chain.ProcessChain;
import util.chain.ProcessContext;

import job.JPAHelper;

import models.Fault;
import models.Job;

public class FaultManager implements Shutdownable{

	private final BlockingQueue<Fault> faultQueue;
	
	private final Chain detectChain;
	
	private final Chain processChain;
	
	private final Executor executor;
	
	private final Thread thread;
	
	private volatile boolean isStarted;
	
	public FaultManager(){
		faultQueue = new LinkedBlockingQueue<Fault>();
		executor = new ThreadPoolExecutor(32, 64, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		detectChain = new DetectChain();
		processChain = new ProcessChain();
		thread = new Thread(new Runnable(){
			public void run(){
				while(isStarted){
					try{
						final Fault fault = faultQueue.poll(1000, TimeUnit.SECONDS);
						if(fault == null){
							continue;
						}else{
							executor.execute(new Runnable(){
								public void run(){
									Context detectContext = new DetectContext(fault);
									try{
										detectChain.execute(detectContext);
										String type = null;
										if(fault.type.equalsIgnoreCase(FaultType.CPU_FAULT)){
											type = JobType.CPU_JOB;
										}else if(fault.type.equalsIgnoreCase(FaultType.HARD_FAULT)){
											type = JobType.HARD_JOB;
										}else if(fault.type.equalsIgnoreCase(FaultType.IO_FAULT)){
											type = JobType.IO_JOB;
										}else if(fault.type.equalsIgnoreCase(FaultType.JVM_FAULT)){
											type = JobType.JVM_JOB;
										}
										Job job = new Job();
										job.appId = fault.appId;
										job.beginTime = new Date();
										job.hostIP = fault.hostIP;
										job.hostName = fault.hostName;
										job.type = type;
										Context processContext = new ProcessContext(job, type);
										processChain.execute(processContext);
										fault.process = true;
										JPAHelper.offer(fault);
										JPAHelper.offer(job);
									}catch(Exception e){
										//do nothing
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
