package test;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class TestAll {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testFastJson();
	}
	
	public static void testFastJson(){
		String jsonString="{versionIndex:1,version:1.0}";
		Map<String, Object> map = JSON.parseObject(jsonString, Map.class);
		String s = (String)map.get("version");
	}

}
