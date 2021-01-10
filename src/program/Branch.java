package program;

import java.io.File;
import java.util.ArrayList;

public class Branch {
	private static final String MASTER = "master";
	private String branchName;

	// 新建默认分支
	public Branch() {
		this(MASTER, true);
	}

	// 以名称创建分支（不切换分支）
	public Branch(String branchName) {
		this(branchName, false);
	}

	// 以名称创建分支（切换分支）
	public Branch(String branchName, boolean isSwitch) {
		this.branchName = branchName;
		if (initBranch(branchName)) {
			if (isSwitch) {
				switchBranch(branchName);
			}
		}
	}

	// 遍历refs.分支，获取分支名列表
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// 判断b1是否为当前分支名
	private static boolean isBranch(String b1) {
		String b = FileOperation.getCurrentBranch();
		if (b1.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	// 初始化分支（新建分支时）
	private static boolean initBranch(String branchName) {
		if (branchName.equals(MASTER)) {
			try {
				new File(FilepathSetting.getLogFilepath() + File.separator + branchName).createNewFile();
				new File(FilepathSetting.getHeadFilepath() + File.separator + branchName).createNewFile();
				ObjectStorage.updateFile(branchName, FileOperation.getCurrentBranchFile(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (getAllBranchName().contains(branchName)) {
			System.out.println("无法创建重名分支");
			return false;
		} else {
			if (FileOperation.getHead().equals("null")) {
				System.out.println("不允许存在两个空分支！");
				return false;
			} else {
				String key = FileOperation.getHead();
				String message = "branch: Created from " + FileOperation.getCurrentBranch();
				String writeline = FileOperation.NULLHash + " " + key + " " + message + "\n";
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
		if (FileOperation.getCurrentBranch().equals(branchName)) {
			return branchName;
		} else if (!getAllBranchName().contains(branchName)) {
			System.out.println("不存在该分支");
			return FileOperation.getCurrentBranch();
		} else {
			// 当前分支的commit与切换分支的commit不相同时才对working tree更新
			if (!FileOperation.getHead().equals(FileOperation.getHead(branchName))) {
				try {
					ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
					String tree = ObjectStorage.searchValue(FileOperation.getHead(branchName)).substring(5, 45);
					ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 更新log文件
			for (String branch : getAllBranchName()) {
				if (branch.equals(branchName)) {
					String message = "Switch: " + FileOperation.getCurrentBranch() + " -> " + branchName;
					String key = FileOperation.getHead();
					String writeline = key + " " + key + " " + message + "\n";
					try {
						ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
						ObjectStorage.updateFile(branchName, FileOperation.getCurrentBranchFile(), false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return branchName;
				}
			}
		}
		return FileOperation.getCurrentBranch();
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
				String key = FileOperation.getHead();
				String writeline = key + " " + key + " " + message + "\n";
				try {
					// 改HEAD
					ObjectStorage.updateFile(newBranchName, FileOperation.getCurrentBranchFile(), false);
					// 加logs.HEAD
					ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 重命名refs.分支
				f2.renameTo(new File(f2.getParent() + File.separator + newBranchName));
				// 重命名logs.refs.分支
				f1.renameTo(new File(f1.getParent() + File.separator + newBranchName));
				// 加logs.refs.分支
				try {
					ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
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

	public String getBranchName() {
		return branchName;
	}

}
