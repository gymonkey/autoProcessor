package util.chain;

import java.util.concurrent.CountDownLatch;

import models.Fault;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import type.FaultType;
import util.ServerConnector;

public class HardDetectCommand implements Command{

	@Override
	public boolean execute(Context context) throws Exception {
		Fault fault = (Fault)context.get("FAULT");
		try{
			ServerConnector.notifyServer(fault.hostIP);
			return Command.CONTINUE_PROCESSING;
		}catch(Exception e){
			fault.type = FaultType.HARD_FAULT;
			return Command.PROCESSING_COMPLETE;
		}
	}

}
