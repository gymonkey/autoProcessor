package util.chain;

import java.util.concurrent.CountDownLatch;

import models.Fault;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import type.FaultType;
import util.CmdExecutor;
import util.InstenceGetter;
import util.ServerConnector;

public class IODetectCommand implements Command {

	private static CmdExecutor cmdExecutor = InstenceGetter.cmdExecutor;
	
	@Override
	public boolean execute(Context context) throws Exception {
		Fault fault = (Fault)context.get("FAULT");
		CountDownLatch latch = new CountDownLatch(1);
		try{
			pojo.Command cmd = new pojo.Command(fault.hostIP, ServerConnector.IO_DETECT, latch);
			cmdExecutor.offer(cmd);
			latch.await();
			if(cmd.getResult().equalsIgnoreCase("true")){
				fault.type = FaultType.IO_FAULT;
				return Command.PROCESSING_COMPLETE;
			}else{
				return Command.CONTINUE_PROCESSING;
			}
		}catch(Exception e){
			return Command.CONTINUE_PROCESSING;
		}
	}

}
