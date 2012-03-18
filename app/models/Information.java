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
@Table(name="information")
public class Information extends Model{

	private static final int PAGE_LENGTH = 15;
	
	@Required
	@Column(name="host_ip")
	public String hostIP;
	
	@Required
	@Column(name="host_name")
	public String hostName;
	
	@Column(name="info")
	public String info;
	
	@Required
	@Column(name="type")
	public String type;
	
	@Required
	@Column(name="collect_date")
	public Date collectDate;
	
	public static List<Information> getInfo(long id, String queryDate, int page){
		List<Information> list = Information.find("id=? and date(collectDate)=date(?)", id, queryDate).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Information>(1);
		}else{
			return list;
		}
	}
	
	public static List<Information> getInfo(long id, String queryDate, String type, int page){
		List<Information> list = Information.find("id=? and date(collectDate)=date(?) and type=?", id, queryDate, type).fetch(page, PAGE_LENGTH);
		
		if(list == null){
			return new ArrayList<Information>(1);
		}else{
			return list;
		}
	}
}
