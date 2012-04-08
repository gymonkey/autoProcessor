package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import models.App;
import models.Fault;

import play.mvc.Controller;
import play.mvc.With;
import util.AppManager;

@With(SessionSetter.class)
public class FaultController extends Controller{

	public static void index(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = format.format(cal.getTime());
		
		List<App> appList = AppManager.getApps();
		
		render(appList, queryDate);
	}
	
	public static void viewFaultData(long appID, String queryData, String type, int page){
		List<Fault> list = null;
		
		if(type.equalsIgnoreCase("ALL_TYPE")){
			list = Fault.getFalut(appID, queryData, page);
		}else{
			list = Fault.getFault(appID, queryData, type, page);
		}
		
		renderTemplate("FaultController/faultData.html", list);
	}
}
