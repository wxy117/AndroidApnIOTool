package com.tommy;

import java.util.ArrayList;

import util.MyUtil;

public class ApnGroup {
	private static final String TAG = "ApnGroup";
	private ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String apnGroupName) {
		this.name = apnGroupName;
	}

	public ApnGroup(String apnGroupName, ArrayList<ApnInfo> apnInfoList) {
		this.name = apnGroupName;
		ApnGroup.this.add(apnInfoList);
	}
	
	public int size() {
		return apnInfoList.size();
	}
	
	public void add(ApnInfo apnInfo) {
		if (apnInfo.getGroupName() != null 
				&& apnInfo.getGroupName().equals(this.name)) {
			this.apnInfoList.add(apnInfo);
		}
	}
	
	public void add(ArrayList<ApnInfo> apnInfoList) {
		for (ApnInfo apnInfo : apnInfoList) {
			ApnGroup.this.add(apnInfo);
		}
	}
	
	public boolean remove(ApnInfo apnInfo) {
		if (apnInfo.getGroupName() != null 
				&& apnInfo.getGroupName().equals(this.name)) {
			return this.apnInfoList.remove(apnInfo);
		}
		return false;
	}
	
	public boolean remove(ArrayList<ApnInfo> apnInfoList) {
		boolean res = true;
		for (ApnInfo apnInfo : apnInfoList) {
			if (!ApnGroup.this.remove(apnInfo)) {
				res = false;
			}
		}
		return res;
	}
	
	
	
}
