package task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.service.AgentService;
import com.alnie.tc.service.DeviceService;
public class Task extends TimerTask {
	protected static Logger logger = Logger.getLogger(Task.class);

   public void run()
  {
    logger.debug("定时任务执行了");
    AgentService as = new AgentService();
    DeviceService ds = new DeviceService();
    PageData newestAgentversion;
    PageData devices;
    try {
    	//获取最新版本
    	newestAgentversion = as.selectNewest();
     	//读取所有设备
       	Page page = new Page();
    	page.setStart(1);
    	page.setLimit(9999999);
    	devices = ds.List(page, null);
    	//每个设备发送一次消息
    	List<Map<String, Object>> dvs = devices.getRows();
    	Map<String, Object> dev;
    	String ostype;
    	int versionIndex;
    	String version;
    	String downUrl;
    	
    	for(int i=0; i<dvs.size();i++){
    		dev = dvs.get(i);
    		ostype = (String)dev.get("ostype");
    		versionIndex
    	}
   	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

}

