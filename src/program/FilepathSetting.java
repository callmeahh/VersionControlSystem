package program;

import java.io.File;

class FilepathSetting {
	// 文件存放路径
	private static String targetFilepath = System.getProperty("user.dir");
	private static String filepath = System.getProperty("user.dir") + File.separator+ ".git";
	private static final String objectFilepath = "objects";
	private static final String headFilepath = "refs";
	private static final String logFilepath = "logs";

	public static File getFilepath() {
		return new File(filepath);
	}

	public static boolean isInitialized() {
		try {
			return new File(filepath).exists();			
		}catch(NullPointerException e) {
			return false;
		}
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
		return targetFilepath;
	}
}
