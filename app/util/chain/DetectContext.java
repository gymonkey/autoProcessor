package util.chain;

import models.Fault;

import org.apache.commons.chain.impl.ContextBase;

public class DetectContext extends ContextBase{
	
	public DetectContext(Fault fault){
		super();
		put("FAULT", fault);
	}

}
