package tommy;

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
	private ApnXmlLoader apnXmlLoader = new ApnXmlLoader();
	private ArrayList<String> apnColNameList;
	
	public static void main(String[] args) {
		ApnColNameGetter apnColNameGetter = new ApnColNameGetter();
		ArrayList<String> mList = 
				apnColNameGetter.getApnColNameList("/Users/mac/Desktop/apns-conf.xml");
		for (String string : mList) {
			System.out.println("colName:" + string);
		}
	}
	
	public ArrayList<String> getApnColNameList(String path) {
		if (!MyUtil.isLegalXMLFile(path)) {
			return null;
		}		
		List<Element> elements = apnXmlLoader.getApnElements(path);
		if (elements != null && elements.size() > 0) {
			apnColNameList = getColNameList(elements);
		} else {
			System.out.println("elements is empty");
			return null;			
		}			
		return apnColNameList;
	}
	
	private ArrayList<String> getColNameList(List<Element> elements) {
		if (elements == null || elements.size() == 0) {
			return null;
		} 		
		ArrayList<String> nameList = new ArrayList<String>();
		for (Element element : elements) {
			if (!element.getName().equalsIgnoreCase("apn")) {continue;}
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
	
}
