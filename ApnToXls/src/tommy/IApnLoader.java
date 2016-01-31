package tommy;

import java.util.ArrayList;

public interface IApnLoader {
	public ArrayList<ApnInfo> loadApns(String apnFilePath);
}
