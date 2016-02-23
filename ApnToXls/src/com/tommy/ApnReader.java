package com.tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.Log;
import util.MyUtil;

public class ApnReader implements IApnReader {
	private static final String TAG = "ApnReader";
	
	
	/* (non-Javadoc)
	 * @see tommy.IApnLoader#loadApns(java.lang.String)
	 */
	@Override
	public ArrayList<ApnInfo> readApns(String apnFilePath) {
		ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
		IApnReader apnReader = getApnReader(apnFilePath);
		apnInfoList = apnReader.readApns(apnFilePath);
		Log.d(TAG, "apnInfoList size" + apnInfoList.size());
		return apnInfoList;		
	}

	@Override
	public ArrayList<ApnGroup> readApnsForGroup(String apnFilePath) {
		ArrayList<ApnGroup> apnGroupList = new ArrayList<ApnGroup>();
		IApnReader apnReader = getApnReader(apnFilePath);
		apnGroupList = apnReader.readApnsForGroup(apnFilePath);
		Log.d(TAG, "apnGroupList size" + apnGroupList.size());
		return apnGroupList;	
	}	
	
	@Override
	public ArrayList<String> getGroupNameList(String apnFilePath) {
		ArrayList<String> groupNameList = new ArrayList<String>();
		IApnReader apnReader = getApnReader(apnFilePath);
		groupNameList = apnReader.getGroupNameList(apnFilePath);
		Log.d(TAG, "groupNameList size : " + groupNameList.size());
		return groupNameList;
	}
	
	private IApnReader getApnReader(String apnFilePath) {
		IApnReader apnReader = null;
		if (MyUtil.isLegalXMLFile(apnFilePath)) {
			apnReader = new ApnXmlReader();
		} else if (MyUtil.isLegalXLSFile(apnFilePath)) {
			apnReader = new ApnXlsReader();
		} else {
			Log.d(TAG, "neither xml nor xls file!!!");
		}
		return apnReader;
	}	
	
}
