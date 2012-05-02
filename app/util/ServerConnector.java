package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ServerConnector {

	private static final String USER_NAME = "tom";
	
	private static final String PASS_WORD = "1989308qw";
	
	private static final String NOTIFY_CMD = "sh /home/tom/client.sh";
	
	public static final String IO_DETECT = "sh /home/tom/io_detect.sh";
	
	public static final String CPU_DETECT = "sh /home/tom/cpu_detect.sh";
	
	public static final String JVM_DETECT = "sh /home/tom/jvm_detect.sh";
	
	public static final String IO_PROCESS = "sh /home/tom/io_process.sh";
	
	public static final String CPU_PROCESS = "sh /home/tom/cpu_process.sh";
	
	public static final String JVM_PROCESS = "sh /home/tom/jvm_process.sh";
	
	private static final int RETRIES = 5;
	
	public static String runScriptOnServer(String hostName, String script) throws IOException{
		Connection connection = new Connection(hostName);
		connection.connect();
		
		for(int i = 0; i < RETRIES; ++i){
			boolean isAuthenticated = connection.authenticateWithPassword(USER_NAME, PASS_WORD);
			if(!isAuthenticated && i == RETRIES -1){
				throw new IOException();
			}else if(isAuthenticated){
				break;
			}
		}
		
		Session session = connection.openSession();
		session.execCommand(script);
		
		Reader reader = new BufferedReader(new InputStreamReader(session.getStdout()));
		StringBuffer sb = new StringBuffer();
		char[] buffer = new char[512];
		int count = 0;
		while((count = reader.read(buffer)) != -1){
			sb.append(Arrays.copyOf(buffer, count));
		}
		
		session.close();
		connection.close();
		
		return sb.toString();
	}
	
	public static String notifyServer(String hostName) throws IOException{
		return runScriptOnServer(hostName, NOTIFY_CMD);
	}
	
	public static void main(String[] args) throws Exception{
		String rst = runScriptOnServer("localhost", "lscpu");
		System.out.print(rst);
	}
}
