package program;

import java.io.File;
import java.util.ArrayList;

public class BranchControl {
	private static final String MASTER = "master";
	private static final String HEAD = "HEAD";
	private static final String NULLHash = "0000000000000000000000000000000000000000";

	// 遍历refs.分支，获取分支名列表
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// 获取HEAD文件
	private static File getCurrentBranchFile() {
		return new File(FilepathSetting.getFilepath() + File.separator + HEAD);
	}

	// 通过HEAD文件，获取当前分支名
	protected static String getCurrentBranch() {
		try {
			return ObjectStorage.getContent(getCurrentBranchFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取refs.分支的当前分支文件
	private static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// 获取logs.refs.分支的当前分支文件
	private static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// 获取logs.HEAD文件
	private static File getAllLogFile() {
		return new File(FilepathSetting.getAllLogFilepath() + File.separator + HEAD);
	}

	// 通过refs.分支的当前分支文件，获取当前分支commit的key
	protected static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getContent(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 通过refs.分支的指定分支文件，获取指定分支commit的key
	private static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

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
		String commit = getHead();
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
		String content = getCurrentBranchLog();
		if (content.contains(commit)) {
			return true;
		}
		return false;
	}

	// 获取logs.refs.分支的当前分支文件的内容
	public static String getCurrentBranchLog() {
		String content = null;
		try {
			content = ObjectStorage.getContent(getHeadLogFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(content);
		return content;
	}

	// 获取logs.HEAD文件的内容
	public static String getAllLog() {
		String content = null;
		try {
			content = ObjectStorage.getContent(getAllLogFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(content);
		return content;
	}

	// 由key和message更新logs.refs.分支的当前分支文件，logs.HEAD文件，refs.分支的当前分支文件
	protected static void updateKey(String key, String message) {
		String parent;
		if (getHead().equals("null")) {
			parent = NULLHash;
		} else {
			parent = getHead();
		}
		String writeline = parent + " " + key + " Commit: " + message + "\n";
		try {
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true); // HEADLog文件为追加写入
			ObjectStorage.updateFile(writeline, getAllLogFile(), true); // 每一次commit后更新总体日志文件
			ObjectStorage.updateFile(key, getHeadFile(), false); // HEAD文件为覆盖写入
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断b1是否为当前分支名
	private static boolean isBranch(String b1) {
		String b = getCurrentBranch();
		if (b1.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	// 初始化分支（新建分支时）
	protected static boolean initBranch(String branchName) {
		if (branchName.equals(MASTER)) {
			try {
				new File(FilepathSetting.getLogFilepath() + File.separator + branchName).createNewFile();
				new File(FilepathSetting.getHeadFilepath() + File.separator + branchName).createNewFile();
				ObjectStorage.updateFile(branchName, getCurrentBranchFile(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (getAllBranchName().contains(branchName)) {
			System.out.println("无法创建重名分支");
			return false;
		} else {
			if (getHead().equals("null")) {
				System.out.println("不允许存在两个空分支！");
				return false;
			} else {
				String key = getHead();
				String message = "branch: Created from " + getCurrentBranch();
				String writeline = NULLHash + " " + key + " " + message + "\n";
				try {
					ObjectStorage.updateFile(writeline,
							new File(FilepathSetting.getLogFilepath() + File.separator + branchName), true); // HEADLog文件为追加写入
					ObjectStorage.updateFile(key,
							new File(FilepathSetting.getHeadFilepath() + File.separator + branchName), false); // HEAD文件为覆盖写入
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
	}

	// 切换分支
	public static String switchBranch(String branchName) {
		if (getCurrentBranch().equals(branchName)) {
			return branchName;
		} else if (!getAllBranchName().contains(branchName)) {
			System.out.println("不存在该分支");
			return getCurrentBranch();
		} else {
			// 当前分支的commit与切换分支的commit不相同时才对working tree更新
			if (!getHead().equals(getHead(branchName))) {
				try {
					ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
					String tree = ObjectStorage.searchValue(getHead(branchName)).substring(5, 45);
					ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 更新log文件
			for (String branch : getAllBranchName()) {
				if (branch.equals(branchName)) {
					String message = "Switch: " + getCurrentBranch() + " -> " + branchName;
					String key = getHead();
					String writeline = key + " " + key + " " + message + "\n";
					try {
						ObjectStorage.updateFile(writeline, getAllLogFile(), true);
						ObjectStorage.updateFile(branchName, getCurrentBranchFile(), false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return branchName;
				}
			}
		}
		return getCurrentBranch();
	}

	// 重命名分支
	public static String renameBranch(String branchName, String newBranchName) {
		ArrayList<String> arr = getAllBranchName();
		int exist = 0;
		File f1 = null;
		File f2 = null;
		// 判断分支是否存在
		for (int i = 0; i < arr.size(); i++) {
			if (branchName.equals(arr.get(i))) {
				exist = 1;
				f1 = new File(FilepathSetting.getLogFilepath() + File.separator + branchName);
				f2 = new File(FilepathSetting.getHeadFilepath() + File.separator + branchName);
				break;
			}
		}
		if (exist == 0) {
			System.out.println("该分支不存在，重命名失败");
		}
		// 判断新命名是否重名
		for (int i = 0; i < arr.size(); i++) {
			if (newBranchName.equals(arr.get(i))) {
				exist = -1;
				System.out.println("新命名与已有分支名重名，重命名失败");
				break;
			}
		}
		// 若分支存在且无重名
		if (exist == 1) {
			// 若为当前分支
			if (isBranch(branchName)) {
				String message = "Branch: renamed " + branchName + " to " + newBranchName;
				String key = getHead();
				String writeline = key + " " + key + " " + message + "\n";
				try {
					// 改HEAD
					ObjectStorage.updateFile(newBranchName, getCurrentBranchFile(), false);
					// 加logs.HEAD
					ObjectStorage.updateFile(writeline, getAllLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 重命名refs.分支
				f2.renameTo(new File(f2.getParent() + File.separator + newBranchName));
				// 重命名logs.refs.分支
				f1.renameTo(new File(f1.getParent() + File.separator + newBranchName));
				// 加logs.refs.分支
				try {
					ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return newBranchName;
	}

	// 打印所有分支
	public static String printAllBranches() {
		StringBuilder s = new StringBuilder();
		for (String f : getAllBranchName()) {
			s.append(f + "\n");
		}
		System.out.println(s.toString());
		return s.toString();
	}

	// 回滚n次，修改本地仓库
	protected static int logReset(int n) throws Exception {
		String key = getHead();
		if (n > parentNum(key) || !commitExists(getBefore(n))) {
			System.out.println("该分支不存在此commit，请输入正确的回滚次数");
			return 0;
		} else {
			String message = "Reset: " + "moving to HEAD~" + n;
			String writeline = key + " " + getBefore(n) + " " + message + "\n";
			// 写入logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			// 写入logs.refs.分支
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			// 修改refs.分支
			ObjectStorage.updateFile(getBefore(n), getHeadFile(), false);
			return 1;
		}
	}

	// 回滚到指定commit，修改本地仓库
	protected static int logReset(String commit) throws Exception {
		String key = getHead();
		String before = ObjectStorage.getFullName(commit);
		if (ObjectStorage.getFullName(commit) != null) {
			String message = "Reset: " + "moving to " + commit;
			String writeline = key + " " + before + " " + message + "\n";
			// 写入logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			// 写入logs.refs.分支
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			// 修改refs.分支
			ObjectStorage.updateFile(before, getHeadFile(), false);
			return 1;
		} else {
			return 0;
		}
	}

	// 回滚n次，修改工作区
	protected static void treeReset(int n) throws Exception {
		String before = getHead();
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// 回滚到指定commit，修改工作区
	protected static void treeReset(String commit) throws Exception {
		String before = ObjectStorage.getFullName(commit);
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}
}
