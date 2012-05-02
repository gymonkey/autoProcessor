package util.chain;

import org.apache.commons.chain.impl.ChainBase;

public class DetectChain extends ChainBase{

	public DetectChain(){
		addCommand(new HardDetectCommand());
		addCommand(new CPUDetectCommand());
		addCommand(new JVMDetectCommand());
		addCommand(new IODetectCommand());
	}
	
}
