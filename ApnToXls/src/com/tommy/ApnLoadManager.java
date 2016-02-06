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

public class ApnLoadManager implements IApnLoader {
	private static final String TAG = "ApnLoadManager";
	IApnLoader apnLoader = null;
	
	public static void main(String[] args) {
		ApnLoadManager apnLoadManager = new ApnLoadManager();
		apnLoadManager.loadApns("/Users/mac/Desktop/apns-conf.xml");
	}
	
	/* (non-Javadoc)
	 * @see tommy.IApnLoader#loadApns(java.lang.String)
	 */
	@Override
	public ArrayList<ApnInfo> loadApns(String apnFilePath) {
		ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
		if (MyUtil.isLegalXMLFile(apnFilePath)) {
			apnLoader = new ApnXmlLoader();
		} else if (MyUtil.isLegalXLSFile(apnFilePath)) {
			apnLoader = new ApnXlsLoader();
		} else {
			Log.d(TAG, "neither xml nor xls file!!!");
			return null;
		}
		apnInfoList = apnLoader.loadApns(apnFilePath);
		Log.d(TAG, "apnInfoList size" + apnInfoList.size());
		return apnInfoList;		
	}	
	
}
