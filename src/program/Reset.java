package program;

public class Reset {
	//指定次数的working tree和repository回滚
	public static void resetHard(int n) throws Exception {
		if(BranchControl.logReset(n) == 1) {
			BranchControl.treeReset(n);
		}
	}
	//指定commit的working tree和repository回滚
	public static void resetHard(String commit) throws Exception {
		if(BranchControl.logReset(commit) == 1) {
			BranchControl.treeReset(commit);
		}
	}
	//指定次数的working tree回滚
	public static void resetMixed(int n) throws Exception {
		BranchControl.logReset(n);
	}
	//指定commit的working tree回滚
	public static void resetMixed(String commit) throws Exception {
		BranchControl.logReset(commit);
	}
}
