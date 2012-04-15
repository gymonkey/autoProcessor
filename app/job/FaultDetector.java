package job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Alert;
import models.Fault;
import play.jobs.Job;
import util.FaultManager;

public class FaultDetector extends Job{

	private static final long INTERVAL = 30 * 60;
	
	private static final long CRITICAL = 30;
	
	private static FaultManager faultManager;
	
	public void doJob() throws Exception{
		List<Alert> alerts = Alert.getAlert(INTERVAL);
		Map<String, Long> ipCountMap = new HashMap<String, Long>();
		Map<String, Alert> ipAlertMap = new HashMap<String, Alert>();
		
		for(Alert alert : alerts){
			Long count = ipCountMap.get(alert.hostIP);
			if(count == null){
				ipCountMap.put(alert.hostIP, new Long(1));
				ipAlertMap.put(alert.hostIP, alert);
			}else{
				count += 1;
				ipCountMap.put(alert.hostIP, count);
			}
		}
		
		Date date = new Date();
		for(Map.Entry<String, Long> entry : ipCountMap.entrySet()){
			long count = entry.getValue();
			if(count > CRITICAL){
				Alert alert = ipAlertMap.get(entry.getKey());
				
				Fault fault = new Fault();
				fault.appId = alert.appId;
				fault.collectDate = date;
				fault.hostIP = alert.hostIP;
				fault.hostName = alert.hostName;
				fault.reason = "错误发生" + count + "次";
				fault.process = false;
				fault.save();
				
				faultManager.offer(fault);
			}
		}
	}
	
}
