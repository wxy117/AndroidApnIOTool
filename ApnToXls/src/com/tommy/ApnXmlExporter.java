package com.tommy;

import static util.MyUtil.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.Log;

public class ApnXmlExporter implements IApnExporter {
	private static final String TAG = "ApnXmlExporter";

	@Override
	public boolean exportApns(ArrayList<ApnInfo> apnInfoList,
			String apnDirPath, int fileType) {
		if (isEmptyArrayList(apnInfoList) || !isDirectory(apnDirPath)) {
			Log.d(TAG, "apnInfoList is empty or apnDirPath is not dir : " + apnDirPath);
			return false;
		}
		Document document = DocumentHelper.createDocument();
		document.addComment(ApnExportManager.COMMENT_HEAD1);
		document.addComment(ApnExportManager.COMMENT_HEAD2);
		document.addComment(ApnExportManager.COMMENT_HEAD3);
		Element rootElement = document.addElement("apns").addAttribute("version", "8");
		addApnInfoListToElement(apnInfoList, rootElement);
		for (ApnInfo apnInfo : apnInfoList) {
			Element apnElement = rootElement.addElement("apn");
			apnInfo.addApnElementAttribute(apnElement);
		}
		try {
			writeApnToXml(document, apnDirPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean addApnNodesToExistFlie(ArrayList<ApnInfo> apnInfoList,
			String apnFilePath) {
		if (isEmptyArrayList(apnInfoList) || !isLegalXMLFile(apnFilePath)) {
			Log.d(TAG, "addApnNodesToExistXml():apnInfoList is null : " + (apnInfoList == null));
			Log.d(TAG, "addApnNodesToExistXml():apnFilePath" + apnFilePath);
			return false;
		}
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new File(apnFilePath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if (document == null) return false;
		Element rootElement = document.getRootElement();
		if(!rootElement.getName().equals("apns")) {
			Log.d(TAG, "root name is not apns! root name: " + rootElement.getName());
			return false;
		}
		Log.d(TAG, "root size" + rootElement.elements().size());
		Log.d(TAG, "apnInfoList size" + apnInfoList.size());
		addApnInfoListToElement(apnInfoList, rootElement);
		Log.d(TAG, "root size" + rootElement.elements().size());
		try {
			writeApnToXml(document, apnFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	public void writeApnToXml(Document document, String apnFilePath) throws Exception {  
        // 紧凑的格式  
//         OutputFormat format = OutputFormat.createCompactFormat();  
         OutputFormat format = OutputFormat.createPrettyPrint();  
//         OutputFormat format = new OutputFormat();
        // 排版缩进的格式  
//        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
//         format.setNewLineAfterNTags(1);
        format.setEncoding("utf-8");  
//        format.setNewLineAfterNTags(1);
//      format.setSuppressDeclaration(true);
//      format.setIndent(true); //设置是否缩进
//     // format.setIndent(" "); //以空格方式实现缩进
      format.setNewlines(true); //设置是否换行
        // 创建XMLWriter对象,指定了写出文件及编码格式  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
        String apnFileDir = null;
        if (isFile(apnFilePath) && isExists(apnFilePath)) {
        	apnFileDir = apnFilePath.substring(0, apnFilePath.lastIndexOf('/') + 1);
        } else if (isDirectory(apnFilePath)) {
        	apnFileDir = apnFilePath;
        }
        Log.d(TAG, "apnFileDir: " + apnFileDir);
        if (apnFileDir == null) {
        	return;
        }
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File(apnFileDir + "apn-conf-new.xml")), "UTF-8"), format);  
        // 写入  
        writer.write(document);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
    }
	
	private void addApnInfoListToElement(ArrayList<ApnInfo> apnInfoList, Element element) {
		for (ApnInfo apnInfo : apnInfoList) {
			Element apnElement = element.addElement("apn");
			apnInfo.addApnElementAttribute(apnElement);
		}
	}
	


}
