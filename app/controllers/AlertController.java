package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import models.Alert;
import models.App;

import play.mvc.Controller;
import play.mvc.With;
import util.AppManager;

@With(SessionSetter.class)
public class AlertController extends Controller{

	public static void index(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = format.format(cal.getTime());
		
		List<App> appList = AppManager.getApps();
		
		render(queryDate, appList);
	}
	
	public static void viewAlertData(long appID, String queryDate, String type, int page){
		List<Alert> alertList = null;
		if(type.equalsIgnoreCase("ALL_TYPE")){
			alertList = Alert.getAlert(appID, queryDate, page);
		}else{
			alertList = Alert.getAlert(appID, queryDate, type, page);
		}
		
		renderTemplate("AlertController/alertData.html", alertList);
	}
}
