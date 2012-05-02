package util.chain;

import org.apache.commons.chain.impl.ChainBase;

public class ProcessChain extends ChainBase{
	
	public ProcessChain(){
		addCommand(new HardProcessCommand());
		addCommand(new IOProcessCommand());
		addCommand(new CPUProcessCommand());
		addCommand(new JVMProcessCommand());
	}

}
