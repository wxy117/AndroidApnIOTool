package test;

import java.util.ArrayList;

import tommy.ApnExportManager;
import tommy.ApnInfo;
import tommy.ApnLoadManager;

public class Test {
	public static void main(String[] args) {
		ApnLoadManager apnLoadManager = new ApnLoadManager();
		ApnExportManager apnExportManager = new ApnExportManager();
		ArrayList<ApnInfo> apnInfoList = apnLoadManager.loadApns("/Users/mac/Desktop/apns-conf.xml");
		apnExportManager.addApnNodesToExistFlie(apnInfoList, "/Users/mac/Desktop/apns-conf.xml");
//		apnExportManager.exportApns(apnInfoList, "/Users/mac/Desktop/", ApnExportManager.FILE_TYPE_XML);
	}
}
