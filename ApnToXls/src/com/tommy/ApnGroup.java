package com.tommy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.AbstractNode;

import util.Log;
import util.MyUtil;

public class ApnGroup {
	private static final String TAG = "ApnGroup";
	public static final String APN_GROUP_COMMENT_END = " end";
	private ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
	private String name;
	
	public ArrayList<ApnInfo> getApnInfoList() {
		return apnInfoList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String apnGroupName) {
		this.name = apnGroupName;
	}

	public ApnGroup(String apnGroupName, ArrayList<ApnInfo> apnInfoList) {
		this.name = apnGroupName;
		this.apnInfoList = apnInfoList;
	}
	
	public ApnGroup(String apnGroupName) {
		this.name = apnGroupName;
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

	public static ArrayList<ApnGroup> makeApnGroupList(List<AbstractNode> apnContents,
			ArrayList<String> commentList, HashMap<String, Integer> commentMap) {
		if (MyUtil.isEmptyList(apnContents) || MyUtil.isEmptyList(commentList)) return null;
		if (commentMap == null || commentMap.size() == 0) return null;
		ArrayList<ApnGroup> apnGroupList = new ArrayList<ApnGroup>();
		
		//realCommentList为commentList去除尾group注释后，仅包含group name的列表
		//commentPairMap为commentList中，头group注释与尾group注释相对应的键值对，如：460 ： 460 end
		ArrayList<String> realCommentList = new ArrayList<String>();
		HashMap<String, String> commentPairMap = new HashMap<String, String>();
		for (int i = 0; i < commentList.size(); i++) {
			String comment = commentList.get(i).trim();
			Log.d(TAG, "makeApnGroupList  comment : " + comment);
			if (comment.endsWith(APN_GROUP_COMMENT_END)
					&& commentList.contains(removeCommentEnd(comment))) {
				Log.d(TAG, "has apn group comment pair");
				realCommentList.add(removeCommentEnd(comment));
				commentPairMap.put(removeCommentEnd(comment), comment);
			}
		}
		Log.d(TAG, "realCommentList : " + realCommentList);
		Log.d(TAG, "commentPairMap : " + commentPairMap);
		
		//获取所有group的apn Element的位置区间，并将其转化为APNInfo，生成ApnGroup，再将每个ApnGroup加入apnGroupList中
		for (String comment : realCommentList) {
			int startPos = commentMap.get(comment);
			int endPos = commentMap.get(commentPairMap.get(comment));
			Log.d(TAG, "startPos : " + startPos + " endPos : " + endPos);
			ApnGroup apnGroup = new ApnGroup(comment);
			for (int i = startPos + 1; i < endPos; i++) {
				AbstractNode node = apnContents.get(i);
				if (!(node instanceof Element)) continue;
				Element element = (Element)node;
				if (element == null || !element.getName().equals("apn")) continue;				
				ApnInfo apnInfo = new ApnInfo((Element)node, comment);
				Log.d(TAG, "apnInfo:" + apnInfo);
				apnGroup.add(apnInfo);
			}
			Log.d(TAG, "apnGroup : " + apnGroup);
			apnGroupList.add(apnGroup);
		}		
		Log.d(TAG, "groupList: " + apnGroupList);
		return apnGroupList;
	}
	
	private static String removeCommentEnd(String comment) {
		if (MyUtil.isEmpty(comment)) return null;
		if (comment.length() <= APN_GROUP_COMMENT_END.length()) return null;	
		String result = comment.substring(0, comment.length() - APN_GROUP_COMMENT_END.length()).trim();
		Log.d(TAG, "removeCommentEnd  result : " + result);
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("ApnGroup: name=" + this.name);
		for (ApnInfo apnInfo : apnInfoList) {
			stringBuilder.append(" carrier:" + apnInfo.get("carrier"));
		}
		return stringBuilder.toString();
	}
	
	
	
}
