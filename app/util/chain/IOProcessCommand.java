package util.chain;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import models.Job;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import type.JobType;
import util.CmdExecutor;
import util.InstenceGetter;
import util.ServerConnector;

public class IOProcessCommand implements Command{

	private static CmdExecutor cmdExecutor = InstenceGetter.cmdExecutor;
	
	public boolean execute(Context context) throws Exception {
		String type = (String)context.get("TYPE");
		if(type.equalsIgnoreCase(JobType.IO_JOB)){
			Job job = (Job)context.get("JOB");
			String hostName = job.hostIP;
			CountDownLatch latch = new CountDownLatch(1);
			pojo.Command cmd = new pojo.Command(hostName, ServerConnector.IO_PROCESS, latch);
			cmdExecutor.offer(cmd);
			try{
				latch.await();
				job.endTime = new Date();
			}catch(Exception e){
				// do nothing
			}
			return Command.PROCESSING_COMPLETE;
		}else{
			return Command.CONTINUE_PROCESSING;
		}
	}

}
