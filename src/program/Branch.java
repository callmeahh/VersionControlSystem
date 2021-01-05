package program;

public class Branch {
	private static final String MASTER = "master";
	private String branchName;

	// �½�Ĭ�Ϸ�֧
	public Branch() {
		this(MASTER, true);
	}

	// �����ƴ�����֧�����л���֧��
	public Branch(String branchName) {
		this(branchName, false);
	}

	// �����ƴ�����֧���л���֧��
	public Branch(String branchName, boolean isSwitch) {
		this.branchName = branchName;
		if (BranchControl.initBranch(branchName)) {
			if (isSwitch) {
			BranchControl.switchBranch(branchName);
			}
		} 
	}
	
	public String getBranchName() {
		return branchName;
	}

}
