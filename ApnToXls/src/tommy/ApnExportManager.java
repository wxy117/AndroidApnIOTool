package tommy;

import java.util.ArrayList;

import util.Log;
import util.MyUtil;
import static util.MyUtil.*;

public class ApnExportManager implements IApnExporter {
	private static final String TAG = "ApnExportManager";
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
		if (isEmptyArrayList(apnInfoList) || !isLegalApnFile(apnFilePath)) {
			Log.d(TAG, "addApnNodesToExistXml():apnInfoList is null : " + (apnInfoList == null));
			Log.d(TAG, "addApnNodesToExistXml():apnFilePath" + apnFilePath);
			return false;
		}
		int apnFileType = getApnFileType(apnFilePath);
		IApnExporter apnExporter = null;
		switch(apnFileType) {
		case FILE_TYPE_XML:
			apnExporter = new ApnXmlExporter();
			break;
		case FILE_TYPE_XLS:
			apnExporter = new ApnXmlExporter();
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
		if (fileType.equalsIgnoreCase("xml")) {
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
