package task;

import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.CommonUtil;

public class StartOnLoad implements ServletContextListener {
	protected static Logger logger = Logger.getLogger(StartOnLoad.class);
	int UPDATE_AGENT_TIMER;
	Timer timer; 
	public static final long NO_DELAY = 0;   
	
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info( "StartOnLoad..............");
		//读取配置
		readConfig();
		//启动定时任务
		timer = new Timer("更新代理通知定时器",true); 
	     timer.schedule(new Task(), NO_DELAY,UPDATE_AGENT_TIMER * 1000);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		

	}
	
	void readConfig(){
		//定义配置文件
		Properties pt=new Properties();
		//定义输入流
		InputStream in=null;
		try{
			//String path = getClass().getResource("/").getPath();//
			in=getClass().getResourceAsStream("/updateagent.properties");
			pt.load(in);
			String str_UPDATE_AGENT_TIMER=pt.getProperty("UPDATE_AGENT_TIMER").trim();
			UPDATE_AGENT_TIMER= Integer.parseInt(str_UPDATE_AGENT_TIMER);
			logger.info( "StartOnLoad UPDATE_AGENT_TIMER:" + UPDATE_AGENT_TIMER);

		}catch (Exception e) {
			logger.error("StartOnLoad readConfig()",e);
		}finally{
			//关闭输入流
			try{
				if(in!=null){
				in.close();
				}
			}catch (Exception e) {
				logger.error("StartOnLoad readConfig()",e);
			}
			
		}
	}

}
