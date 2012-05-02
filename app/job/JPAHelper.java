package job;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import play.db.jpa.Model;
import play.jobs.Job;

public class JPAHelper extends Job{

	private static final BlockingQueue<Model> modelQueue = new LinkedBlockingQueue<Model>();
	
	public void doJob() throws Exception{
		while(true){
			try{
				Model model = modelQueue.poll(100, TimeUnit.MILLISECONDS);
				if(model == null){
					break;
				}else{
					model.refresh();
				}
			}catch(InterruptedException e){
				//do nothing but break the loop
				break;
			}
		}
	}
	
	public static void offer(Model model){
		modelQueue.offer(model);
	}
	
}
