package tommy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import util.Log;
import util.MyUtil;

public class ApnXmlLoader implements IApnLoader {
	private static final String TAG = "ApnXmlLoader";

	/* (non-Javadoc)
	 * @see tommy.IApnLoader#loadApns(java.lang.String)
	 */
	@Override
	public ArrayList<ApnInfo> loadApns(String apnFilePath) {
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
			apnInfoList.add(apnInfo);
		}			
	    return apnInfoList;
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
