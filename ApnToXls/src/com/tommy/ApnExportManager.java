package com.tommy;

import java.util.ArrayList;

import util.Log;
import util.MyUtil;
import static util.MyUtil.*;

public class ApnExportManager implements IApnExporter {
	private static final String TAG = "ApnExportManager";
	public static final String COMMENT_HEAD1 = "\n/*\n** Copyright 2006, Google Inc.\n"
			+ "**\n** Licensed under the Apache License, Version 2.0 (the \"License\");\n"
			+ "** you may not use this file except in compliance with the License.\n"
			+ "** You may obtain a copy of the License at"
			+ "\n"
			+ "**\n"
			+ "**     http://www.apache.org/licenses/LICENSE-2.0\n"
			+ "**\n"
			+ "** Unless required by applicable law or agreed to in writing, software\n"
			+ "** distributed under the License is distributed on an \"AS IS\" BASIS,\n"
			+ "** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
			+ "** See the License for the specific language governing permissions and\n"
			+ "** limitations under the License.\n"
			+ "*/\n";
	public static final String COMMENT_HEAD2 = " use empty string to specify no proxy or port ";
	public static final String COMMENT_HEAD3 = " This version must agree with that in apps/common/res/apns.xml ";
	public static final int FILE_TYPE_XML = 0;
	public static final int FILE_TYPE_XLS = 1;
	
	public static void main(String[] args) {
		//int type = getApnFileType("/Users/mac/Desktop/apns-conf.XML");
		int type = getApnFileType("/Users/mac/Desktop/test.XLS");
		Log.d(TAG, "" + type);
	}
	
	@Override
	public boolean exportApns(ArrayList<ApnInfo> apnInfoList, String apnDirPath, int fileType) {
		if (isEmptyArrayList(apnInfoList) || !isLegalDirPath(apnDirPath)) {
			Log.d(TAG, "exportApns():apnInfoList is null : " + (apnInfoList == null));
			Log.d(TAG, "exportApns():apnDirPath" + apnDirPath);
			return false;
		}
		IApnExporter apnExporter = null;
		switch(fileType) {
		case FILE_TYPE_XML:
			apnExporter = new ApnXmlExporter();
			break;
		case FILE_TYPE_XLS:
			apnExporter = new ApnXlsExporter();
			break;
		default:
			break;
		}
		if (apnExporter != null) {
			return apnExporter.exportApns(apnInfoList, apnDirPath, fileType);
		}
		return false;		
	}
	
	@Override
	public boolean addApnNodesToExistFlie(ArrayList<ApnInfo> apnInfoList, String apnFilePath) {
		Log.d(TAG, "addApnNodesToExistXml():apnInfoList size : " + apnInfoList.size());
		if (isEmptyArrayList(apnInfoList) || !isLegalApnFile(apnFilePath)) {
			Log.d(TAG, "addApnNodesToExistXml():apnInfoList is null : " + (apnInfoList == null));
			Log.d(TAG, "addApnNodesToExistXml():apnFilePath " + apnFilePath);
			return false;
		}
		int apnFileType = getApnFileType(apnFilePath);
		IApnExporter apnExporter = null;
		switch(apnFileType) {
		case FILE_TYPE_XML:
			apnExporter = new ApnXmlExporter();
			break;
		case FILE_TYPE_XLS:
			apnExporter = new ApnXlsExporter();
			break;
		default:				
			break;
		}
		if (apnExporter != null) {
			return apnExporter.addApnNodesToExistFlie(apnInfoList, apnFilePath);
		}		
		return false;
	}
	
	public static int getApnFileType(String apnFilePath) {
		if (!MyUtil.isFile(apnFilePath)) {		
			System.out.println("illegal File path : " + apnFilePath);
			return -1;
		} else if (!MyUtil.isExists(apnFilePath)) {
			System.out.println("file does not exists!! File path : " + apnFilePath);
			return -1;
		}
		
		String fileType = apnFilePath.substring(apnFilePath.lastIndexOf(".") + 1, apnFilePath.length());
		Log.d(TAG, "fileType:" + fileType);
		if (fileType.equalsIgnoreCase("xml")) {
			Log.d(TAG, "" + FILE_TYPE_XML);
			return FILE_TYPE_XML;
		} else if (fileType.equalsIgnoreCase("xls")) {
			return FILE_TYPE_XLS;
		}		
		return -1;
	}
	
	public static boolean isLegalApnFile(String apnFilePath) {
		int fileType = getApnFileType(apnFilePath);
		if (fileType == FILE_TYPE_XML || 
				fileType == FILE_TYPE_XLS) {
			return true;
		}
		return false;
	}
	
	
	
}
