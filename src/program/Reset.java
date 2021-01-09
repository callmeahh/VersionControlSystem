package program;

public class Reset {
	// 获取当前分支commit的parent个数
	private static int parentNum(String commit) throws Exception {
		int count = 0;
		while (ObjectStorage.searchValue(commit).charAt(46) == 'p') {
			String par = ObjectStorage.searchValue(commit).substring(53, 93);
			commit = par;
			count++;
		}
		return count;
	}

	// 获取当前分支commit指定次数的parent
	private static String getBefore(int n) throws Exception {
		String commit = FileOperation.getHead();
		String par = ObjectStorage.searchValue(commit).substring(53, 93);
		n--;
		for (int i = 0; i < n; i++) {
			commit = par;
			par = ObjectStorage.searchValue(commit).substring(53, 93);
		}
		return par;
	}

	// 判断commit是否在logs.refs.分支的当前分支文件的内容中
	private static boolean commitExists(String commit) {
		String content = Log.getCurrentBranchLog();
		if (content.contains(commit)) {
			return true;
		}
		return false;
	}

	// 回滚n次，修改本地仓库
	private static int logReset(int n) throws Exception {
		String key = FileOperation.getHead();
		if (n > parentNum(key) || !commitExists(getBefore(n))) {
			System.out.println("该分支不存在此commit，请输入正确的回滚次数");
			return 0;
		} else {
			String message = "Reset: " + "moving to HEAD~" + n;
			String writeline = key + " " + getBefore(n) + " " + message + "\n";
			// 写入logs.HEAD
			ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
			// 写入logs.refs.分支
			ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
			// 修改refs.分支
			ObjectStorage.updateFile(getBefore(n), FileOperation.getHeadFile(), false);
			return 1;
		}
	}

	// 回滚到指定commit，修改本地仓库
	private static int logReset(String commit) throws Exception {
		String key = FileOperation.getHead();
		String before = ObjectStorage.getFullName(commit);
		if (ObjectStorage.getFullName(commit) != null) {
			String message = "Reset: " + "moving to " + commit;
			String writeline = key + " " + before + " " + message + "\n";
			// 写入logs.HEAD
			ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
			// 写入logs.refs.分支
			ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
			// 修改refs.分支
			ObjectStorage.updateFile(before, FileOperation.getHeadFile(), false);
			return 1;
		} else {
			return 0;
		}
	}

	// 回滚n次，修改工作区
	private static void treeReset(int n) throws Exception {
		String before = FileOperation.getHead();
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// 回滚到指定commit，修改工作区
	private static void treeReset(String commit) throws Exception {
		String before = ObjectStorage.getFullName(commit);
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// 指定次数的working tree和repository回滚
	public static void resetHard(int n) {
		try {
			if (logReset(n) == 1) {
				treeReset(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定commit的working tree和repository回滚
	public static void resetHard(String commit) {
		try {
			if (logReset(commit) == 1) {
				treeReset(commit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定次数的repository回滚
	public static void resetMixed(int n) {
		try {
			logReset(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定commit的repository回滚
	public static void resetMixed(String commit) {
		try {
			logReset(commit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
