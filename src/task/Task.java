package task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alnie.tc.network.NetworkOp;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.service.AgentService;
import com.alnie.tc.service.DeviceService;
import com.pkq.firewall.message.response.Response;

public class Task extends TimerTask {
	protected static Logger logger = Logger.getLogger(Task.class);

	public void run() {
		notifyNewAgent();
	}

	static public void notifyNewAgent() {
		logger.debug("定时任务执行了");
		AgentService as = new AgentService();
		DeviceService ds = new DeviceService();
		PageData newestAgentversions;
		PageData devices;
		Map<String, Object> nav;
		try {
			//获取最新版本
			newestAgentversions = as.selectNewest();

			//读取所有设备
			Page page = new Page();
			page.setStart(1);
			page.setLimit(9999999);
			devices = ds.List(page, null);
			//每个设备发送一次消息
			List<Map<String, Object>> dvs = devices.getRows();

			Map<String, Object> dev;
			String ostype;
			String devip;

			for (int i = 0; i < dvs.size(); i++) {
				dev = dvs.get(i);
				ostype = (String) dev.get("ostype");
				nav = getNewVersionByOs(ostype, newestAgentversions.getRows());
				if (nav != null) {
					devip = (String) dev.get("ip");
					NetworkOp.newAgentNotify(devip, nav);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取自定操作系统类型的最新版本数据
	 * @param os
	 * @param navs
	 * @return
	 */
	static Map<String, Object> getNewVersionByOs(String os,
			List<Map<String, Object>> navs) {
		String ostype;
		for (Map<String, Object> nav : navs) {
			ostype = (String) nav.get("ostype");
			if (os.equals(ostype)) {
				return nav;
			}
		}
		return null;
	}

}
