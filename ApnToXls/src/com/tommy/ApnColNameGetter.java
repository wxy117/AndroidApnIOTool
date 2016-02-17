package com.tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import util.MyUtil;

public class ApnColNameGetter {
	private ApnXmlReader apnXmlLoader = new ApnXmlReader();
	
	public static void main(String[] args) {
		ApnColNameGetter apnColNameGetter = new ApnColNameGetter();
		ArrayList<String> mList = 
				apnColNameGetter.getApnColNameList("/Users/mac/Desktop/apns-conf.xml");
		for (String string : mList) {
			System.out.println("colName:" + string);
		}
		apnColNameGetter.printApnAttrName();
	}
	
	public ArrayList<String> getApnColNameList(String path) {				
		return getApnColNameList(path, "apn", true);
	}
	
	/**
	 * @param path
	 * @param elementName
	 * @param isIgnoreCase
	 * @return
	 */
	public ArrayList<String> getApnColNameList(String path, String elementName, boolean isIgnoreCase) {
		
		if (!MyUtil.isLegalXMLFile(path)) {
			return null;
		}		
		List<Element> elements = apnXmlLoader.getApnElements(path);
		ArrayList<String> apnColNameList;
		if (elements != null && elements.size() > 0) {
			apnColNameList = getColNameList(elements, elementName, isIgnoreCase);
		} else {
			System.out.println("elements is empty");
			return null;			
		}			
		return apnColNameList;
	}
	
	/**
	 * @param elements
	 * @param elementName
	 * @param isIgnoreCase
	 * @return
	 */
	private ArrayList<String> getColNameList(List<Element> elements, String elementName, boolean isIgnoreCase) {
		if (elements == null || elements.size() == 0) {
			return null;
		} 		
		ArrayList<String> nameList = new ArrayList<String>();
		for (Element element : elements) {
			if ((isIgnoreCase && !element.getName().equalsIgnoreCase(elementName)) || 
					(!isIgnoreCase && !element.getName().equals(elementName))) {
				continue;
			} 
			if (!element.getName().equalsIgnoreCase(elementName)) {continue;}
			List<DefaultAttribute> attrList = (List<DefaultAttribute>)element.attributes();
			if (attrList == null || attrList.size() == 0) {continue;}
			for (DefaultAttribute attr : attrList) {
				String colName = attr.getName();
				if(!nameList.contains(colName)) {
					nameList.add(colName);
				}
			}
		}
		return nameList;		
	}
	
	/**
	 * 
	 */
	private void printApnAttrName() {
		ApnColNameGetter apnColNameGetter = new ApnColNameGetter();
		ArrayList<String> mList = 
				apnColNameGetter.getApnColNameList("/Users/mac/Desktop/apns-conf.xml");
		for (String string : mList) {
			System.out.print("\"" + string + "\", ");
		}
	}
	
	
	
}
