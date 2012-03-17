package controllers;

import play.mvc.Controller;

public class AlertController extends Controller{

	public static void index(){
		render();
	}
	
	public static void viewAlertData(long appID, String queryDate){
		renderTemplate("AlertController/alertData.html");
	}
}
