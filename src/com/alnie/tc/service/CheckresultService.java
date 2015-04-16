package com.alnie.tc.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.utils.CommonUtil;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class CheckresultService extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("checkresult.list",baseMap);
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("checkresult.total",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
	public HashMap Det(HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			HashMap<String, Object> map= (HashMap)this.getSqlMapClientTemplate().queryForObject("checkresult.det",baseMap);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
	}
	public String Stat(HashMap baseMap)throws Exception{
		HashMap reMap=new HashMap();
		try {
			List listx=new ArrayList();
			List listy=new ArrayList();
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("checkresult.statList",baseMap);
			String chart_caption="检测结果统计";
			String chart_xaxis_name="资源IP";
			String chart_yaxis_name="变更数量";
			StringBuilder kpiXML=new StringBuilder();
			kpiXML.append("<chart caption='").append(chart_caption).append("' xAxisName='").append(chart_xaxis_name).append("' yAxisName='").append(chart_yaxis_name).append("' showNames='1' decimalPrecision='0' formatNumberScale='0'>");
			for(Map map:resultList){
				kpiXML.append("<set name='").append(map.get("resourceip")).append("' value='").append(map.get("ct")).append("' color='").append(CommonUtil.GetHRandomColor()).append("' />");
			}
			kpiXML.append("</chart>");
			logger.info(kpiXML);
			return kpiXML.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
	}
}

