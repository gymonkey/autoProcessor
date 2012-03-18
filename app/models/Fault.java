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
@Table(name="fault")
public class Fault extends Model{

	private static final int PAGE_LENGTH = 15;
	
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
	@Column(name="reason")
	public String reason;
	
	@Required
	@Column(name="collect_date")
	public Date collectDate;
	
	@Required
	@Column(name="process")
	public short process;
	
	@Required
	@Column(name="type")
	public String type;
	
	public static List<Fault> getFalut(long id, String queryDate, int page){
		List<Fault> list = Fault.find("id=? and date(collectDate)=date(?)", id, queryDate).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Fault>(1);
		}else{
			return list;
		}
	}
	
	public static List<Fault> getFault(long id, String queryDate, String type, int page){
		List<Fault> list = Fault.find("id=? and date(collectDate)=date(?) and type=?", id, queryDate, type).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Fault>(1);
		}else{
			return list;
		}
	}
}
