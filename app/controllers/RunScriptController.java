package controllers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import play.mvc.Controller;

public class RunScriptController extends Controller{

	private static final ConcurrentMap<String, String> ipScriptMap = new ConcurrentHashMap<String, String>();
	
	public static void index(){
		render();
	}
	
}
