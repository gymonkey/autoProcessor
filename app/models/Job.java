package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="job")
public class Job extends Model{
	
	private static final int PAGE_SIZE = 15;
	
	@Required
	@Column(name="app_id")
	public long appId;
	
	@Required
	@Column(name="host_ip")
	public String hostIP;
	
	@Required
	@Column(name="host_name")
	public String hostName;
	
	@Required
	@Column(name="type")
	public String type;
	
	@Required
	@Column(name="result")
	public String result;
	
	@Required
	@Column(name="begin_time")
	public Date beginTime;
	
	@Column(name="end_time")
	public Date endTime;
	
	public static List<Job> getJobs(long appID, String queryDate, int page){
		List<Job> jobs = Job.find("id=? and date(beginTime)=date(?)", appID, queryDate).fetch(page, PAGE_SIZE);
		if(jobs == null){
			return new ArrayList<Job>(1);
		}else{
			return jobs;
		}
	}
	
	public static List<Job> getJobs(long appID, String queryDate, String type, int page){
		List<Job> jobs = Job.find("id=? and date(beginTime)=date(?) and type=?", appID, queryDate, type).fetch(page, PAGE_SIZE);
		if(jobs == null){
			return new ArrayList<Job>(1);
		}else{
			return jobs;
		}
	}
}
