package controllers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

import play.mvc.Controller;
import pojo.Command;
import util.CmdExecutor;
import util.InstenceGetter;
import util.ServerConnector;

public class RunScriptController extends Controller{
	
	private static CmdExecutor cmdExecutor = InstenceGetter.cmdExecutor;
	
	public static void index(){
		render();
	}
	
	public static void verify(String hostName){
		try{
			ServerConnector.runScriptOnServer(hostName, "ls");
			ok();
		}catch(Exception e){
			notFound();
		}
	}
	
	public static void runScript(String hostName, String script){
		CountDownLatch latch = new CountDownLatch(1);
		Command cmd = new Command(hostName, script, latch);
		cmdExecutor.offer(cmd);
		try{
			latch.await();
			Controller.renderText(cmd.getResult().replaceAll("\n", "<br/>"));
		}catch(Exception e){
			notFound();
		}
	}
}
