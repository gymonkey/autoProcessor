package util;

public class InstenceGetter {
	
	public static final FaultManager faultManager = new FaultManager();
	
	public static final CmdExecutor cmdExecutor = new CmdExecutor();
	
	static{
		faultManager.start();
		cmdExecutor.start();
	}
	
}
