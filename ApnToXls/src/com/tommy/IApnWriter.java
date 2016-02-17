package com.tommy;

import java.util.ArrayList;

public interface IApnWriter {
	public boolean writeApns(ArrayList<ApnInfo> apnInfoList, String apnDirPath, int fileType);
	public boolean writeApnsForGroup(ArrayList<ApnGroup> apnGroupList, String apnDirPath, int fileType);
	public boolean addApnNodesToExistFlie(ArrayList<ApnInfo> apnInfoList, String apnFilePath);
}
