package program;

import java.io.File;
import java.util.ArrayList;

public class BranchControl {
	private static final String MASTER = "master";
	private static final String HEAD = "HEAD";
	private static final String NULLHash = "0000000000000000000000000000000000000000";

	// 获取分支列表
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// 获得记录当前分支名的文件
	private static File getCurrentBranchFile() {
		return new File(FilepathSetting.getFilepath() + File.separator + HEAD);
	}

	// 返回当前分支
	protected static String getCurrentBranch() {
		try {
			return ObjectStorage.getContent(getCurrentBranchFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取当前分支头指针文件
	private static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// 获取当前分支日志文件
	private static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// 获得总日志文件
	private static File getAllLogFile() {
		return new File(FilepathSetting.getAllLogFilepath() + File.separator + HEAD);
	}

	// 获取当前分支头指针
	protected static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getContent(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	//获取指定分支头指针
	private static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	// 得到当前分支日志的内容
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

	// 得到总日志内容
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

	// 更新分支头指针文件和分支日志文件（每一次commit后）
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

	// TODO：重命名分支
	public static String renameBranch(String branchName, String newBranchName) {
		return newBranchName;
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
			for (String branch : getAllBranchName()) {
				if(!getHead().equals(getHead(branchName))){
					try {
						ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
						String tree = ObjectStorage.searchValue(getHead(branchName)).substring(5, 45);
						ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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

	// 打印所有分支
	public static String printAllBranches() {
		StringBuilder s = new StringBuilder();
		for (String f : getAllBranchName()) {
			s.append(f + "\n");
		}
		System.out.println(s.toString());
		return s.toString();
	}

}
