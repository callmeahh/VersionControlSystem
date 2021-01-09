package program;

import java.io.File;
import java.util.ArrayList;

public class FileOperation {
	private static final String HEAD = "HEAD";
	protected static final String NULLHash = "0000000000000000000000000000000000000000";

	// 遍历refs.分支，获取分支名列表
	protected static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// 获取HEAD文件
	protected static File getCurrentBranchFile() {
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
	protected static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// 获取logs.refs.分支的当前分支文件
	protected static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// 获取logs.HEAD文件
	protected static File getAllLogFile() {
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
	protected static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
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

}
