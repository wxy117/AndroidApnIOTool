package util;

import java.io.File;
import java.util.ArrayList;

import com.tommy.ApnWriter;

import jxl.format.Colour;

public class MyUtil {
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	 
	public static boolean isLegalWorkNumber(String value) {
		if (value.length() != 8) {
			return false;
		}
		if (isInteger(value)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isLegalIpAddress(String value) {
		if (value.length() > 15 || value.length() < 10) {
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) > '9' || value.charAt(i) < '0') {
				if (value.charAt(i) == '.') {
					continue;
				}
				return false;
			}
		}
		return true;
	}
	 
	public static boolean isExists(String path) {
		if (isEmpty(path)) {
			return false;
		}
		File f=new File(path);		
		if (f.exists()) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFile(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);		
		if (f.isFile()) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isDirectory(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);		
		if (f.isDirectory()) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isAbsolute(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);		
		if (f.isAbsolute()) {
			return true;
		}else{
			return false;
		}
	}
	
	public static String getFatherPath(String path){
		if(!isAbsolute(path)){
			System.out.println("���Ǿ���·���� ·����" + path);
			return null;
		}
		int position = 0;
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				position = i;
			}
		}
		if (position > 0) {
			System.out.println("���ļ���·��Ϊ��" + path.substring(0, position));
			return path.substring(0, position);
		}
		else{
			System.out.println("·����û�з�б��");
			return null;
		}		
	}
	
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0 || isEmpty(substring)) {
		return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isEmpty(String string){
		if (string == null || string.equals("")) {
			return true;
		}
		return false;
	}
	
	public static int getStringWordCount(String string){
		if (isEmpty(string)) {
			return 0;
		}
		int count = string.split(" |,|\\?|\\|.|!").length;
//		int count = 0;
//		for (int i = 0; i < string.trim().length(); i++) {
//			char a = string.trim().charAt(i);
//			if (!((a >= 'a' && a<= 'z') || (a >= 'A' && a <= 'Z'))){
//
//				count++;
//			}
//		}
		return count;
	}
	
	public static void log(String tag, String log){
		System.out.println(tag + ":   " + log);
	}
	
	public static String getReferenceXlsPath(String path, String name){
		if (MyUtil.isEmpty(path) || MyUtil.isEmpty(name)) {
			return null;
		}
		int dotPos = -1;
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '.') {
				dotPos = i;
			}
		}
		if (dotPos != -1) {
			return (path.substring(0, dotPos) + name + path.substring(dotPos, path.length()));
		}
		return null;
	}
	
	public static class Color extends Colour{
		public Color(int val, String s, int r, int g, int b) {
			super(val, s, r, g, b);
			// TODO Auto-generated constructor stub
		}
	}
	
	private static int count = 0;
	public static int getSubStringCount(String str, String subStr){
		if (count != 0) {
			count = 0;
		}
		return getSubStringCountImpl(str, subStr);
	}
	private static int getSubStringCountImpl(String str, String subStr)     
	{         
		if (str.indexOf(subStr) == -1)         
		{             
			return 0;         
		}         
		else if(str.indexOf(subStr) != -1)         
		{             
			count++;             
			getSubStringCountImpl(str.substring(str.indexOf(subStr) + subStr.length()), subStr);             
			return count;         
			}         
		return 0;     
	} 
	
	public static String getXmlNode(String values){
		if (isEmpty(values)) {
			return null;
		}
		String str = "<xliff";
		int begin = values.indexOf("<xliff");
		int end = begin;
//		String nodeStr = values.substring(begin, values.length());
		if (begin != -1) {
			for (int i = begin + str.length(); i < values.length(); i++) {
				if (values.charAt(i) == '>') {
					end = i;
				}
			}
			if (end > begin) {
				return values.substring(begin, end + 1);
			}
			
		}
		return null;
	}
	
	
	
	public static boolean isLegalXMLFile(String xmlFilepath) {
		int fileType = ApnWriter.getApnFileType(xmlFilepath);
		if (fileType == ApnWriter.FILE_TYPE_XML) {
			return true;
		}		
		return false;
	}
	
	public static boolean isLegalXLSFile(String xlsFilepath) {
		int fileType = ApnWriter.getApnFileType(xlsFilepath);
		if (fileType == ApnWriter.FILE_TYPE_XLS) {
			return true;
		}		
		return false;
	}
	
	
	
	public static boolean isEmptyArrayList(ArrayList<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;	
	}
	
	public static boolean isLegalDirPath(String dirPath) {
		if (isExists(dirPath) && isDirectory(dirPath)) {
			return true;
		} 
		return false;
	}
	
}
