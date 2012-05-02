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
@Table(name="alert")
public class Alert extends Model{

	private static final int PAGE_LENGTH = 15;
	
	@Required
	@Column(name="host_ip")
	public String hostIP;
	
	@Required
	@Column(name="host_name")
	public String hostName;
	
	@Required
	@Column(name="info")
	public String info;
	
	@Required
	@Column(name="collect_date")
	public Date collectDate;
	
	@Required
	@Column(name="app_id")
	public long appId;

	@Column(name="type")
	public String type;
	
	@Column(name="isProcess")
	public boolean isProcess;
	
	public static List<Alert> getAlert(long app_id, String queryDate, int page){
		List<Alert> list = Alert.find("app_id=? and date(collectDate)=date(?)", app_id, queryDate).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Alert>(1);
		}else{
			return list;
		}
	}
	
	public static List<Alert> getAlert(long app_id, String queryDate, String type, int page){
		List<Alert> list = Alert.find("app_id=? and date(collectDate)=date(?) and type=?", app_id, queryDate, type).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Alert>(1);
		}else{
			return list;
		}
	}
	
	public static List<Alert> getAlert(long interval){
		long now = System.currentTimeMillis() / 1000;
		
		List<Alert> list = Alert.find("unix_timestamp(collectDate) > ? and unix_timestamp < ? and isProcess = false", now - interval, now).fetch();
		if(list == null){
			return new ArrayList<Alert>(1);
		}else{
			return list;
		}
	}
}
