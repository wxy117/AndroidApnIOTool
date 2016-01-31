package tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.MyUtil;

public class ApnLoadManager implements IApnLoader {
	private ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();
	IApnLoader apnLoader = null;
	
	public static void main(String[] args) {
		ApnLoadManager apnLoadManager = new ApnLoadManager();
		
		apnLoadManager.loadApns("/Users/mac/Desktop/apns-conf.xml");
	}
	
	@Override
	public ArrayList<ApnInfo> loadApns(String apnFilePath) {
		if (MyUtil.isLegalXMLFile(apnFilePath)) {
			apnLoader = new ApnXmlLoader();
		} else if (MyUtil.isLegalXLSFile(apnFilePath)) {
			apnLoader = new ApnXlsLoader();
		} else {
			System.out.println("neither xml nor xls file!!!");
			return null;
		}
		apnLoader.loadApns(apnFilePath);
		return apnInfoList;		
	}

	
	
	
}
