package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class SessionSetter extends Controller{

	@Before
	public static void setSession(){
		if(request.cookies.get("autoProcess.username") != null){
			String username = request.cookies.get("autoProcess.username").value;
			session.put("username", username);
		}
	}
}
