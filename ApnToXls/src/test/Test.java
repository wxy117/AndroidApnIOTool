package test;

import java.util.ArrayList;

import com.tommy.ApnGroup;
import com.tommy.ApnWriter;
import com.tommy.ApnInfo;
import com.tommy.ApnReader;

public class Test {
	public static void main(String[] args) {
		ApnReader apnLoadManager = new ApnReader();
		ApnWriter apnExportManager = new ApnWriter();
//		ArrayList<ApnInfo> apnInfoList = apnLoadManager.readApns("/Users/mac/Desktop/apns-conf.xml");
//		apnExportManager.addApnNodesToExistFlie(apnInfoList, "/Users/mac/Desktop/apns-conf.xml");
//		apnExportManager.exportApns(apnInfoList, "/Users/mac/Desktop/", ApnExportManager.FILE_TYPE_XML);
//		ArrayList<ApnGroup> apnGroupList = apnLoadManager.readApnsForGroup("/Users/mac/Desktop/apns-conf2.xml");
//		apnExportManager.writeApnsForGroup(apnGroupList, "/Users/mac/Desktop/", ApnWriter.FILE_TYPE_XML);
		System.out.println("apn group name : " + apnLoadManager.getGroupNameList("/Users/mac/Desktop/apns-conf2.xml"));
	}
}
