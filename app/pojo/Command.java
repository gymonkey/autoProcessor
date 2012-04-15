package pojo;

import java.util.concurrent.CountDownLatch;

public class Command {
	
	private String hostName;
	
	private String cmd;
	
	private String result;
	
	private CountDownLatch latch;
	
	public Command(String hostName, String cmd, CountDownLatch latch){
		this.hostName = hostName;
		this.cmd = cmd;
		this.latch = latch;
	}
	
	public void setHostName(String hostName){
		this.hostName = hostName;
	}
	
	public void setCmd(String cmd){
		this.cmd = cmd;
	}
	
	public void setResult(String result){
		this.result = result;
	}
	
	public String getHostName(){
		return hostName;
	}
	
	public String getCmd(){
		return cmd;
	}
	
	public CountDownLatch getLatch(){
		return latch;
	}
	
	public String getResult(){
		return result;
	}
}
