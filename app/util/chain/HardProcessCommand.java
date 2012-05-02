package util.chain;

import java.util.List;

import models.App;
import models.Job;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import type.JobType;

public class HardProcessCommand implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		String type = (String)context.get("TYPE");
		if(type.equalsIgnoreCase(JobType.HARD_JOB)){
			Email email = new SimpleEmail();
			email.setFrom("lee.gymonkey");
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("lee.gymonkey@gmail.com", "1989308qw");
			email.setTLS(true);
			Job job = (Job)context.get("JOB");
			List<String> mailBoxes = App.getMailBox(job.appId);
			for(String mailBox : mailBoxes){
				email.addTo(mailBox);
			}
			email.setCharset("utf-8");
			email.setMsg("机器:" + job.hostIP + "发生硬件故障，请检查 ");
			email.send();
			return Command.PROCESSING_COMPLETE;
		}else{
			return Command.CONTINUE_PROCESSING;
		}
	}

}
