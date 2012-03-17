package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="alert")
public class Alert extends Model{

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
	
	@Required
	@Column(name="type")
	public String type;
}
