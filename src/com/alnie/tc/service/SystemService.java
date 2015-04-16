package com.alnie.tc.service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Menu;
import com.alnie.tc.system.common.BaseService;
import com.ibatis.sqlmap.client.SqlMapSession;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class SystemService extends BaseService{
	public List GetMenu(long parentFunctionId,long operatorId)throws Exception{
		List menuList = new ArrayList();
		try {
			Map pMap=new HashMap();
			pMap.put("operatorId", operatorId);
			pMap.put("parentFunctionId", parentFunctionId);
			List<Map<String, Object>> list= (List)this.getSqlMapClientTemplate().queryForList("system.getSysFunction",pMap);
			Menu menu = null;
			Map attributes=null;
		    for(Map obj:list){
		    	menu = new Menu();
		    	menu.setId(ParseString(obj.get("function_id")));
		    	menu.setText(ParseString(obj.get("function_name")));
		    	menu.setParent_id(ParseString(obj.get("parent_function_id")));
		    	menu.setIconCls(ParseString(obj.get("function_class")));
		    	menu.setState((ParseInteger(obj.get("type"))==1)?"":"closed");
		    	attributes=new HashMap();
		    	attributes.put("isLeaf",(ParseInteger(obj.get("type"))==1)?true:false);
		    	attributes.put("url", obj.get("href"));
		    	menu.setAttributes(attributes);
		    	menuList.add(menu);
		    };
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			
		}
	    return menuList;
	}
	public AjaxResult GetMenuFunctionTitle(long functionId)throws Exception{
		StringBuilder buf=new StringBuilder();
		try {
			Map pMap=new HashMap();
			pMap.put("function_id", functionId);
			List<Map<String, Object>> list= (List)this.getSqlMapClientTemplate().queryForList("system.getMenuFunctionTitle",pMap);
			Map obj=null;
			for (int i = 0; i < list.size(); i++) {
		    	obj=(Map)list.get(i);
		    	buf.append(obj.get("function_name"));
		    	if(i<list.size()-1)buf.append(">>");
		    };
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
		}
		AjaxResult ar=new AjaxResult(AjaxResult.RESULT_CODE_SUCCESS,buf.toString());
		ar.setUncut_result_msg(buf.toString());
		return ar;
	}
	public List SystemConfigList(HashMap baseMap)throws Exception{
		List resultList = new ArrayList();
		try {
			Map pMap=new HashMap();
			pMap.put("config_group_id", baseMap.get("configGroupId"));
			pMap.put("config_id", baseMap.get("configId"));
			pMap.put("config_value", baseMap.get("configValue"));
			List<Map<String, Object>> list= (List)this.getSqlMapClientTemplate().queryForList("system.getSystemConfigList",pMap);
		    for(Map oList:list){
				Map map=new HashMap();
				map.put("configId",ParseString(oList.get("config_id")));
				map.put("configValue",ParseString(oList.get("config_value")));
				resultList.add(map);
			}
		    if("1".equals(ParseString(baseMap.get("needAll")))){
		    	Map map=new HashMap();
				map.put("configId","");
				map.put("configValue","所有");
		    	resultList.add(0,map);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
		}
	    return resultList;
	}
	public List SystemRole(HashMap baseMap)throws Exception{
		List resultList = new ArrayList();
		try {
			List<Map<String, Object>> list= (List)this.getSqlMapClientTemplate().queryForList("system.role",baseMap);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
		}
	}
	public List SystemRolePrivilege(int roleId, int nodeId) throws Exception {
		List result = new ArrayList();
		StringBuilder buf = new StringBuilder();
		HashMap args = new HashMap<String, String>();
		List<Map<String, Object>> ret= (List)this.getSqlMapClientTemplate().queryForList("system.privilegeSysFunction",nodeId);
		HashMap node=null;
		HashMap nodeAttrs=null;
		HashMap tmp = null;
		int pageId=0;
		int type=0;
		String name=null;
		String webaddr=null;
		for (int i = 0; i < ret.size(); i++) {
			node = new HashMap();
			nodeAttrs = new HashMap();
			tmp = (HashMap) ret.get(i);
			pageId=ParseInteger(tmp.get("function_id"));
			type=ParseInteger(tmp.get("type"));
			name=ParseString(tmp.get("function_name"));
			webaddr=ParseString(tmp.get("href"));

			node.put("id", pageId);
			node.put("text", name);
			node.put("attributes", nodeAttrs);

			buf = new StringBuilder();
			args = new HashMap();
			args.put("access_id", roleId);
			args.put("function_id", pageId);
			int ct= (Integer)this.getSqlMapClientTemplate().queryForObject("system.privilegeSysRole",args);
			if (ct>0) {
				node.put("checked", true);
			}
			if (type==0) { // 文件夹
				node.put("state", "open");
				nodeAttrs.put("url", "");
				nodeAttrs.put("isLeaf", false);
				node.put("children", SystemRolePrivilege(roleId,pageId));
			} else {
				nodeAttrs.put("url", webaddr);
				nodeAttrs.put("isLeaf", true);
			}
			result.add(node);
		}
		return result;
	}
	public AjaxResult SystemRolePrivilegeAdd(HashMap baseMap) throws Exception {
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    int roleId=ParseInteger(baseMap.get("roleId"));
		    session.update("system.privilegeDel",roleId);
		    Object privilegeStr=baseMap.get("privilegeStr");
			if (IsNotNull(privilegeStr)) {
				// 插入最新的角色权限
				String[] privilegesAry = ParseString(privilegeStr).split("\6");
				List privilegeList = new ArrayList();
				Map map=null;
				for (int i = 0; i < privilegesAry.length; i++) {
					map=new HashMap();
					map.put("access_id", roleId);
					map.put("function_id", ParseInteger(privilegesAry[i]));
					session.insert("system.privilegeAdd", map);
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
}


