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
	IApnReader apnReader = null;
	
	public static void main(String[] args) {
		ApnReader apnReader = new ApnReader();
		apnReader.readApns("/Users/mac/Desktop/apns-conf.xml");
	}
	
	/* (non-Javadoc)
	 * @see tommy.IApnLoader#loadApns(java.lang.String)
	 */
	@Override
	public ArrayList<ApnInfo> readApns(String apnFilePath) {
		ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
		if (MyUtil.isLegalXMLFile(apnFilePath)) {
			apnReader = new ApnXmlReader();
		} else if (MyUtil.isLegalXLSFile(apnFilePath)) {
			apnReader = new ApnXlsReader();
		} else {
			Log.d(TAG, "neither xml nor xls file!!!");
			return null;
		}
		apnInfoList = apnReader.readApns(apnFilePath);
		Log.d(TAG, "apnInfoList size" + apnInfoList.size());
		return apnInfoList;		
	}	
	
}
