package com.alnie.tc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.SysLogs;
import com.alnie.tc.po.UploadInfo;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.CommonUtil;
import com.alnie.tc.system.utils.UploadUtil;
import com.fox.Device;
import com.fox.ICClient;
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
public class ResourceService extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("resource.list",baseMap);

			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("resource.total",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
	public AjaxResult New(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("resource.newCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前资源已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    baseMap.put("id",UUID.randomUUID().toString());
			session.insert("resource.new", baseMap);
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RESOURCE,"新增资源","资源IP："+baseMap.get("ip")+" 文件路径："+baseMap.get("filepath")));
			conn.commit();
			
			//发送到设备上
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,0,"新增资源",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult Mod(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("resource.modCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前资源已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.update("resource.mod", baseMap);
		    session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RESOURCE,"修改资源","资源IP："+baseMap.get("ip")+" 文件路径："+baseMap.get("filepath")));
		    conn.commit();
			//发送到设备上
			String devIp = baseMap.get("ip").toString();
			HashMap baseMap2 = new HashMap();
			baseMap2.put("ip", devIp);
		    List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("resource.getbydevice",baseMap2);
			//Device dev = new Device(devIp);
			ICClient icc = new ICClient();
			icc.changePolicy(devIp,resultList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,0,"修改资源",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult Del(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
			session.update("resource.del", baseMap);
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RESOURCE,"删除资源","资源IP："+baseMap.get("ip")+" 文件路径："+baseMap.get("filepath")));
			conn.commit();
			//发送到设备上
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,0,"删除资源",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public HashMap Det(HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			HashMap<String, Object> map= (HashMap)this.getSqlMapClientTemplate().queryForObject("resource.det",baseMap);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
	}
	public AjaxResult BatchImport(HashMap baseMap,UploadInfo upInfo)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		String uploadDir=CommonUtil.GetProConfig(Constants.UPLOAD_PATH);
		UploadUtil uu = new UploadUtil(upInfo);
		uu.setUseStoreName(true);
		uu.setUploadEncoding("UTF-8");
		uu.setUploadDir(uploadDir);
		String upResult = uu.Upload();
		AjaxResult aResult=new AjaxResult();
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
			if (upResult.indexOf("操作成功") > -1) {
				int importColumnCnt=4;
				int column=0;
				int cnt = 0;			//文件行数
				int sucCnt = 0;		//数据导入成功的条数
				int errCnt = 0;		//数据导入失败的条数
				StringBuilder errDesc = null;			//错误描述
				StringBuilder errHint = new StringBuilder();				//错误提示
				String strStk = ",";
				
				String file_name=Constants.GetRealPath(uploadDir+(String)uu.getUfInfo().getFileStoreName().get(0));
				//读取文件
				FileReader file = new FileReader(file_name);
				BufferedReader buff = new BufferedReader(file);	
				//开始读取文件行信息
				boolean eof = false;
				String line="";
				String[] strarray =null;
				StringTokenizer tokenizer = null;
				boolean isPass=true;
				String ip=ParseString(baseMap.get("ip"));
				String resourceip=null;
				String filetype=null;
				String filepath=null;
				String description=null;
				Map map=null;
				//导入格式：资源IP,文件路径,文件类型描述,文件类型,描述,添加时间
				while(!eof){
					cnt++;
					line = "";
					line = buff.readLine();
					if(cnt<=1)continue;
					
					if(line == null) eof = true;
					else{
						strarray = line.split(strStk,-1);
						isPass=true;
						try{
							for(column=0;column<importColumnCnt-1;column++){
								if(column==0||column==1||column==3){//资源IP,文件路径,文件类型 不可为空
									if(strarray[column]==null||"".equals(strarray[column])){
										errHint.append("第").append(cnt).append("行数据错误:第").append(column+1).append("列不能是空值").append(SEPARATOR);
										isPass=false;
									}
								}
							}
						}catch(Exception e){
							errHint.append("第").append(cnt).append("行数据错误:").append( e.toString()).append(SEPARATOR);
							isPass=false;
						}
						
						resourceip = strarray[0];
						filepath = strarray[1];
						filetype = strarray[3];
						description= strarray[4];
						
						if(!resourceip.equals(ip)){
							isPass=false;
							errHint.append("第").append(cnt).append("行数据错误:").append("资源IP不正确").append(SEPARATOR);
						}
						if(!isPass){
							errCnt += 1;
							continue;
						}
						sucCnt += 1;
						map=new HashMap();
						map.put("id",UUID.randomUUID().toString());
						map.put("ip", resourceip);
						map.put("filepath", filepath);
						map.put("filetype", filetype);
						map.put("description", description);
						session.insert("resource.new", map);
					}
				}
				aResult.setResult_code(AjaxResult.RESULT_CODE_SUCCESS);
				aResult.setResult_msg(GetResultMsg(sucCnt,errCnt,errHint.toString()));
				if(sucCnt>0){
					session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RESOURCE,"导入资源","导入数量："+sucCnt));
				}
				conn.commit();
				//发送到设备上
				

				return aResult;
			}else{
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,upResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,0,"导入资源",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
	}
	public AjaxResult Export(HashMap baseMap)throws Exception{
		try {
			StringBuilder exportString=new StringBuilder();
			exportString.append("资源IP,文件路径,文件类型描述,文件类型,描述,添加时间\r\n");
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("resource.exportList",baseMap);
			String columnStr="ip,filepath,filetype_desc,filetype,description,addedtime";
			String[] columnAry=columnStr.split(",");
			for(Map map:resultList){
				for(int i=0;i<columnAry.length;i++){
					if(i==columnAry.length-1)
						exportString.append(ParseString(map.get(columnAry[i])).replaceAll(",", " ")).append("\r\n");//端口号用空格分隔
					else exportString.append(ParseString(map.get(columnAry[i])).replaceAll(",", " ")).append(",");
				}
			}
			String export_path=CommonUtil.GetProConfig(Constants.EXPORT_PATH);
			CommonUtil.WriteFile(Constants.GetRealPath(export_path+"resourceExport.csv"), exportString.toString());
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,"导出资源",""));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RESOURCE,0,"导出资源",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
		}
		return new AjaxResult();
	}
}
