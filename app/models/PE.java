package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="pe")
public class PE extends Model{

	@Required
	@Column(name="name")
	public String name;
	
	@Required
	@Column(name="app_id")
	public long appID;
	
	@Required
	@Column(name="admin")
	public boolean isAdmin;
	
	@Required
	@Column(name="passwd")
	public String passwd;
}
