package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import models.App;
import models.Information;

import play.mvc.Controller;
import play.mvc.With;
import util.AppManager;

@With(SessionSetter.class)
public class InfoController extends Controller{

	public static void index(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = format.format(cal.getTime());
		
		List<App> appList = AppManager.getApps();
		
		render(appList, queryDate);
	}
	
	public static void viewInfoData(long appID, String queryDate, String type, int page){
		List<Information> list = null;
		
		if(type.equalsIgnoreCase("ALL_TYPE")){
			list = Information.getInfo(appID, queryDate, page);
		}else{
			list = Information.getInfo(appID, queryDate, type, page);
		}
		
		renderTemplate("InfoController/infoData.html", list);
	}
}
