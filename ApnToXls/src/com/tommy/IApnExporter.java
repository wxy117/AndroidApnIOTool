package com.tommy;

import java.util.ArrayList;

public interface IApnExporter {
	public boolean exportApns(ArrayList<ApnInfo> apnInfoList, String apnDirPath, int fileType);
	public boolean addApnNodesToExistFlie(ArrayList<ApnInfo> apnInfoList, String apnFilePath);
}
