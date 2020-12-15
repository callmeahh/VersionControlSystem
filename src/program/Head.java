package program;

import java.io.File;
import java.io.IOException;

public class Head {
	private static String headPath = ObjectStorage.getFilepath();
	private static final String HEAD = "HEAD";
	private static final String HEADLog = "HEADLog";

	// 对HEAD文件进行初始化
	protected static boolean init() {
		if (!getHeadFile().exists()) {
			String path = headPath + File.separator + HEAD;
			File f = new File(path);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	// 更新HEAD文件和HEADLog文件（每一次commit后）
	public static void updateKey(String key, String message) {
		String parent;
		if (getHead().equals("null")) {
			parent = "0000000000000000000000000000000000000000";
		} else {
			parent = getHead();
		}
		String writeline = parent + " " + key + " " + message + "\n";
		try {
			ObjectStorage.storeFromString(HEADLog, writeline, true); // HEADLog文件为追加写入
			ObjectStorage.storeFromString(HEAD, key, false); // HEAD文件为覆盖写入
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取HEAD文件
	private static File getHeadFile() {
		String path = headPath + File.separator + HEAD;
		File f = new File(path);
		return f;
	}

	// 获取HEADLog文件
	private static File getHeadLogFile() {
		String path = headPath + File.separator + HEADLog;
		File f = new File(path);
		return f;
	}

	// 得到当前HEAD文件中的内容
	public static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getString(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 得到当前HEADLog文件中的内容
	public static String getHeadlog() {
		String str = null;
		try {
			str = ObjectStorage.getString(getHeadLogFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
