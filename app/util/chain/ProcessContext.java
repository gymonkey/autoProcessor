package util.chain;

import models.Job;

import org.apache.commons.chain.impl.ContextBase;

public class ProcessContext extends ContextBase{

	public ProcessContext(Job job, String type){
		super();
		this.put("JOB", job);
		this.put("TYPE", type);
	}
	
}
