package controllers;

import models.PE;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import play.mvc.Controller;

public class LoginController extends Controller{
	
	public static void login(String username, String passwd){
		passwd = Base64.base64Decode(passwd);
		
		boolean isValidate = PE.validate(username, passwd);
		if(isValidate){
			response.setCookie("autoProcess.username", username.split("@")[0]);
			renderText("/autoProcess");
		}else{
			notFound();
		}
	}
	
	public static void logout(){
		response.removeCookie("autoProcess.username");
		renderTemplate("AlertController/index.html");
	}

}
