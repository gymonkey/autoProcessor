package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="app")
public class App extends Model{

	@Required
	@Column(name="app_name")
	String appName;
	
	@Required
	@Column(name="related_pe")
	String relatedPE;
	
	@Required
	@Column(name="related_mailbox")
	String relatedMailBox;
	
	public static List<App> getAllApp(){
		List<App> list = App.findAll();
		
		if(list == null){
			return new ArrayList<App>(1);
		}else{
			return list;
		}
	}
}
