package com.tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.AbstractNode;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultComment;

import util.Log;
import util.MyUtil;

public class ApnXmlReader implements IApnReader {
	private static final String TAG = "ApnXmlLoader";
	
	public static void main(String[] args) {
		ApnXmlReader apnXmlReader = new ApnXmlReader();
		apnXmlReader.readApnsForGroup("/Users/mac/Desktop/apns-conf2.xml");
	}

	/* (non-Javadoc)
	 * @see tommy.IApnLoader#loadApns(java.lang.String)
	 */
	@Override
	public ArrayList<ApnInfo> readApns(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}
		ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();

		List<Element> apnElements = getApnElements(apnFilePath);
		Log.d(TAG, "size: " + apnElements.size());
		for (Element element : apnElements) {
			ApnInfo apnInfo = getRow(element);
			if (apnInfo == null) {
                Log.d(TAG, "apnInfo is null");
                continue;
            }
//			Log.d(TAG, "apnInfoList size" + apnInfoList.size());
			apnInfoList.add(apnInfo);
		}			
	    return apnInfoList;
	}
	
	@Override
	public ArrayList<ApnGroup> readApnsForGroup(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}
		List<AbstractNode> apnContents = getApnContents(apnFilePath);
		return getApnGroupList(apnContents);
	}
	
	private ArrayList<ApnGroup> getApnGroupList(List<AbstractNode> apnContents) {
		ArrayList<String> commentList = new ArrayList<String>();
		HashMap<String, Integer> commentMap = new HashMap<>();		
		for (int index = 0; index < apnContents.size(); index++) {
			if (apnContents.get(index) instanceof DefaultComment) {
				String commentContent = ((DefaultComment)apnContents.get(index)).getText().trim();
				Log.d(TAG, "comment: " + commentContent);
				commentList.add(commentContent);
				commentMap.put(commentContent, index);
			}
//			Log.d(TAG, "element: " + apnContents.get(index).getNodeTypeName());
		}
		if (commentList.size() == 0) return null;		
		return ApnGroup.makeApnGroupList(apnContents, commentList, commentMap);
	}
	
	@Override
	public ArrayList<String> getGroupNameList(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}
		ArrayList<String> groupNameList = new ArrayList<String>();
		ArrayList<ApnGroup> apnGroupList = readApnsForGroup(apnFilePath);
		for (ApnGroup apnGroup : apnGroupList) {
			groupNameList.add(apnGroup.getName());
		}
		return groupNameList;
	}

	/**
	 * @param apnFilePath
	 * @return
	 */
	public List<Element> getApnElements(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}		
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(apnFilePath));
			Element root = document.getRootElement();
			if(!root.getName().equals("apns")) {
				Log.d(TAG, "root name is not apns! root name: " + root.getName());
				return null;
			}
			List<Element> apnElements = root.elements();
			Log.d(TAG, "size: " + apnElements.size());
	        return apnElements;
		} catch (DocumentException e) {
			Log.d(TAG, "xml file parse failed!!!");
			return null;
		}		
	}
	
	public List<AbstractNode> getApnContents(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}		
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(apnFilePath));
			Element root = document.getRootElement();
			if(!root.getName().equals("apns")) {
				Log.d(TAG, "getApnContent()  root name is not apns! root name: " + root.getName());
				return null;
			}
			List<AbstractNode> apnContents = new ArrayList<AbstractNode>();
			List list = root.content();
			for (Object content : list) {
				if (content instanceof Element || content instanceof Comment) {
					apnContents.add((AbstractNode)content);
				}
//				Log.d(TAG, "getApnContent()  content : " + content.toString());
			}			
			Log.d(TAG, "getApnContent()  content size: " + apnContents.size());
	        return apnContents;
		} catch (DocumentException e) {
			Log.d(TAG, "xml file parse failed!!!");
			return null;
		}		
	}
	
	
	/**
	 * @param element
	 * @return
	 */
	private ApnInfo getRow(Element element) {
		if (element == null || !element.getName().equals("apn")) {
			return null;
		}
		ApnInfo apnInfo = new ApnInfo();
		for (String key : ApnInfo.APN_KEY_ARRAY) {
			String value = element.attributeValue(key);
			if (value == null) {
				continue;
			}
			apnInfo.put(key, value);
		}	
		return apnInfo;
	}	

}
