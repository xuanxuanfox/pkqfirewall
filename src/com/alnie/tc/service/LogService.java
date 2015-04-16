package com.alnie.tc.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.system.common.BaseService;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class LogService extends BaseService{
	public PageData LoginList(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("log.loginList",baseMap);
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("log.loginTotal",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("log.list",baseMap);
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("log.total",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
}
