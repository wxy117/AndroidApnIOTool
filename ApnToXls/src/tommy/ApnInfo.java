package tommy;

import java.util.HashMap;

import util.MyUtil;

public class ApnInfo {
	public static String[] APN_KEY_ARRAY = {"carrier", "mcc", "mnc", "apn"};
	private HashMap<String, String> infoMap = new HashMap<String, String>();
	
	public void put(String key, String value){
		if (!MyUtil.isIn(key, APN_KEY_ARRAY)) {
			
			return;
		}
		infoMap.put(key, value);
	}
	
	public String get(String key) {
		if (!MyUtil.isIn(key, APN_KEY_ARRAY)) {
			
			return null;
		}
		return infoMap.get(key);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name = null;
	
	
	
	

}
