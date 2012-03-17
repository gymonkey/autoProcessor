package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="job")
public class Job extends Model{
	
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
}
