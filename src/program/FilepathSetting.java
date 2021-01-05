package program;

import java.io.File;

class FilepathSetting {
	// 本地仓库文件存放路径
	private static String filepath = "D:\\0.课程资料\\java\\commit history";
	private static final String objectFilepath = "objects";
	private static final String headFilepath = "refs";
	private static final String logFilepath = "logs";
	private static final String info = "Info";
	private static String targetFilepath;

	// 设置本地仓库文件保存路径
	public static void setFilepath(String filepath) {
		FilepathSetting.filepath = filepath;
	}

	// 获取本地仓库文件夹，存放logs，objects，refs文件夹和HEAD，Info文件，HEAD文件记录当前分支，Info文件记录工作区路径
	public static File getFilepath() {
		return new File(filepath);
	}

	// 获取objects文件夹，存放blob，tree，commit文件
	protected static File getObjectFilepath() {
		return new File(filepath + File.separator + objectFilepath);
	}

	// 获取refs文件夹，存放各分支文件，记录各分支当前commit的key
	protected static File getHeadFilepath() {
		return new File(filepath + File.separator + headFilepath);
	}

	// 获取logs文件夹，存放refs文件夹和HEAD文件，HEAD文件记录所有历史记录
	protected static File getAllLogFilepath() {
		return new File(filepath + File.separator + logFilepath);
	}

	// 获取logs.refs文件夹，存放各分支文件，记录各分支历史记录
	protected static File getLogFilepath() {
		return new File(filepath + File.separator + logFilepath + File.separator + headFilepath);
	}

	// 初始化目标文件夹，生成objects文件夹，refs文件夹，logs文件夹，logs.refs文件夹
	protected static void InitRepo() {
		File[] dirs = new File[] { getFilepath(), getObjectFilepath(), getHeadFilepath(), getAllLogFilepath(),
				getLogFilepath() };
		for (File f : dirs) {
			f.mkdirs();
		}
	}

	// 获取工作区文件路径
	public static String getTargetFilepath() {
		try {
			return ObjectStorage.getContent(new File(filepath + File.separator + info));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFilepath;
	}

	// 设置工作区文件路径
	public static void setTargetFilepath(String targetFilepath) {
		FilepathSetting.targetFilepath = targetFilepath;
		try {
			ObjectStorage.storeFromString(info, targetFilepath, filepath, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
