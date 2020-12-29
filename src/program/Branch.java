package program;

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
		if (BranchControl.initBranch(branchName)) {
			if (isSwitch) {
			BranchControl.switchBranch(branchName);
			}
		} 
	}
	// TODO：重命名分支
	public String renameBranch(String newName) {
		this.branchName = newName;
		return newName;
	}
	
	public String getBranchName() {
		return branchName;
	}

}
