package program;

import java.io.File;

class FilepathSetting {
	// �ļ����·��
	private static String filepath = "D:\\0.�γ�����\\java\\commit history";
	private static final String objectFilepath = "objects";
	private static final String headFilepath = "refs";
	private static final String logFilepath = "logs";
	private static final String info = "Info";
	private static String targetFilepath;

	// �����ļ�����·��
	public static void setFilepath(String filepath) {
		FilepathSetting.filepath = filepath;
	}

	public static File getFilepath() {
		return new File(filepath);
	}

	protected static File getObjectFilepath() {
		return new File(filepath + File.separator + objectFilepath);
	}

	protected static File getHeadFilepath() {
		return new File(filepath + File.separator + headFilepath);
	}

	protected static File getAllLogFilepath() {
		return new File(filepath + File.separator + logFilepath);
	}

	protected static File getLogFilepath() {
		return new File(filepath + File.separator + logFilepath + File.separator + headFilepath);
	}

	protected static void InitRepo() {
		File[] dirs = new File[] { getFilepath(), getObjectFilepath(), getHeadFilepath(), getAllLogFilepath(),
				getLogFilepath() };
		for (File f : dirs) {
			f.mkdirs();
		}
	}

	public static String getTargetFilepath() {
		try {
			return ObjectStorage.getContent(new File(filepath + File.separator + info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return targetFilepath;
	}

	public static void setTargetFilepath(String targetFilepath) {
		FilepathSetting.targetFilepath = targetFilepath;
		try {
			ObjectStorage.storeFromString(info, targetFilepath, filepath, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
