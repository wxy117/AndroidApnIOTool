package tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import util.MyUtil;

public class ApnXmlLoader implements IApnLoader {

	@Override
	public ArrayList<ApnInfo> loadApns(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}
		ArrayList<ApnInfo> apnInfoList = new ArrayList<ApnInfo>();

		List<Element> apnElements = getApnElements(apnFilePath);
		System.out.println("size: " + apnElements.size());
		for (Element element : apnElements) {
			ApnInfo apnInfo = getRow(element);
			if (apnInfo == null) {
                   System.out.println("apnInfo is null");
                   continue;
            }
			apnInfoList.add(apnInfo);
		}			
	    return apnInfoList;
	}
	
	public List<Element> getApnElements(String apnFilePath) {
		if (!MyUtil.isLegalXMLFile(apnFilePath)) {			
			return null;
		}		
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(apnFilePath));
			Element root = document.getRootElement();
			if(!root.getName().equals("apns")) {
				System.out.println("root name is not apns! root name: " + root.getName());
				return null;
			}
			List<Element> apnElements = root.elements();
			System.out.println("size: " + apnElements.size());			
	        return apnElements;
		} catch (DocumentException e) {
			System.out.println("xml file parse failed!!!");
			return null;
		}		
	}
	
	
	private ApnInfo getRow(Element element) {
		if (element == null || !element.getName().equals("apn")) {
			return null;
		}
		
		List list = element.attributes();  
		DefaultAttribute e = null;
        for (int i = 0; i < list.size(); i++)     
        {     
            e = (DefaultAttribute)list.get(i);     
            //System.out.println("name = " + e.getName() + ", value = " + e.getText());   
            System.out.println("name: " + e.getName());
            //xattribute += " [name = " + e.getName() + ", value = " + e.getText() + "]";     
        }     
		
		ApnInfo apnInfo = new ApnInfo();
		for (String key : ApnInfo.APN_KEY_ARRAY) {
			String value = element.attributeValue(key);
			if (value == null) {
				continue;
			}
			apnInfo.put(key, value);
		}	
//		System.out.println("name: " + apnInfo.get("carrier"));
//		System.out.println("name: " + apnInfo.get("mcc"));
//		System.out.println("name: " + apnInfo.get("mnc"));
		return apnInfo;
	}

}
