package com.tommy;

import java.util.ArrayList;

public class ApnXlsWriter implements IApnWriter {

	@Override
	public boolean writeApns(ArrayList<ApnInfo> apnInfoList,
			String apnDirPath, int fileType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addApnNodesToExistFlie(ArrayList<ApnInfo> apnInfoList,
			String apnFilePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeApnsForGroup(ArrayList<ApnGroup> apnGroupList,
			String apnDirPath, int fileType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addApnGroupToExistFile(ArrayList<ApnGroup> apnGroupList,
			String apnFilePath) {
		// TODO Auto-generated method stub
		return false;
	}

}
