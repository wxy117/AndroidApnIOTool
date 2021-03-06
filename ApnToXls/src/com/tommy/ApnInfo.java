package com.tommy;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.Element;

import util.MyUtil;

public class ApnInfo {
	public static String[] APN_KEY_ARRAY = {"carrier", "mcc", "mnc", 
		"apn", "proxy", "port", "mmsproxy", "mmsport", "mmsc", "server", 
		"user", "password", "authtype", "type", "read_only", "ppp_number", 
		"protocol", "roaming_protocol", "spn", "mvno_type", "mvno_match_data"};
	private HashMap<String, String> infoMap = new HashMap<String, String>();
	private ArrayList<String> commentList = new ArrayList<String>();
	private String groupName = null;
	
	public ApnInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public ApnInfo (Element element, String groupName) {
		if (element == null || !element.getName().equals("apn")) {
			return;
		}
		this.groupName = groupName;
		for (String key : ApnInfo.APN_KEY_ARRAY) {
			String value = element.attributeValue(key);
			if (value == null) {
				continue;
			}
			this.put(key, value);
		}	
	}	
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<String> getCommentList() {
		return commentList;
	}
	
	public void addComment(String comment) {
		if (MyUtil.isEmpty(comment)) return;
		commentList.add(comment);
	}
	public void getComment(int index) {
		commentList.get(index);
	}	
	public void removeComment(int index) {
		commentList.remove(index);
	}

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
	
	public Element addApnElementAttribute(Element element) {
		if (element == null) return null;
		element.clearContent();
		if (infoMap == null || infoMap.size() == 0) return null;
		for (String key : APN_KEY_ARRAY) {
			String value = infoMap.get(key);
			if(value == null) continue;
			element.addAttribute(key, value);
		}
		return element;		
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("ApnInfo : [");
		for (String string : APN_KEY_ARRAY) {
			if (infoMap.get(string) != null) {
				stringBuilder.append(string + ":" + infoMap.get(string) + ", ");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();	
	}
	
	

	
	

}
