package com.tommy;

import java.util.ArrayList;

public interface IApnReader {
	public ArrayList<ApnInfo> readApns(String apnFilePath);
	public ArrayList<ApnGroup> readApnsForGroup(String apnFilePath);
}
