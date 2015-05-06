package task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BusinessReport implements org.quartz.Job{
	  public void perform(){ //执行报表统计入口函数
	        //业务逻辑
		  System.out.println("开始执行报表的业务逻辑了----现在的时间是--"+new Date());
		  
	    }

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		perform();
		
	}

}