package program;

import java.io.File;
import java.io.IOException;

public class Head {
	private static String headPath = ObjectStorage.getFilepath();
	private static final String HEAD = "HEAD";
	private static final String HEADLog = "HEADLog";

	// ��HEAD�ļ����г�ʼ��
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

	// ����HEAD�ļ���HEADLog�ļ���ÿһ��commit��
	public static void updateKey(String key, String message) {
		String parent;
		if (getHead().equals("null")) {
			parent = "0000000000000000000000000000000000000000";
		} else {
			parent = getHead();
		}
		String writeline = parent + " " + key + " " + message + "\n";
		try {
			ObjectStorage.storeFromString(HEADLog, writeline, true); // HEADLog�ļ�Ϊ׷��д��
			ObjectStorage.storeFromString(HEAD, key, false); // HEAD�ļ�Ϊ����д��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡHEAD�ļ�
	private static File getHeadFile() {
		String path = headPath + File.separator + HEAD;
		File f = new File(path);
		return f;
	}

	// ��ȡHEADLog�ļ�
	private static File getHeadLogFile() {
		String path = headPath + File.separator + HEADLog;
		File f = new File(path);
		return f;
	}

	// �õ���ǰHEAD�ļ��е�����
	public static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getString(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// �õ���ǰHEADLog�ļ��е�����
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
