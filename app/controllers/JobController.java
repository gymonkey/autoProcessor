package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.App;
import models.Job;

import play.mvc.Controller;
import play.mvc.With;
import util.AppManager;

@With(SessionSetter.class)
public class JobController extends Controller {
	
	public static void index(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = format.format(cal.getTime());
		
		List<App> apps = AppManager.getApps();
		
		render(yesterday, apps);
	}
	
	public static void viewData(long appID, String type, String queryDate, int page){
		List<Job> jobList = null;
		if(type.equalsIgnoreCase("ALL_TYPE")){
			jobList = Job.getJobs(appID, queryDate, page);
		}else{
			jobList = Job.getJobs(appID, queryDate, type, page);
		}
		
		render(jobList);
	}
}
