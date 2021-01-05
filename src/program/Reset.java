package program;

public class Reset {
	// 指定次数的working tree和repository回滚
	public static void resetHard(int n) {
		try {
			if (BranchControl.logReset(n) == 1) {
				BranchControl.treeReset(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定commit的working tree和repository回滚
	public static void resetHard(String commit) {
		try {
			if (BranchControl.logReset(commit) == 1) {
				BranchControl.treeReset(commit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定次数的repository回滚
	public static void resetMixed(int n) {
		try {
			BranchControl.logReset(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 指定commit的repository回滚
	public static void resetMixed(String commit) {
		try {
			BranchControl.logReset(commit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
