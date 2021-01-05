package program;

public class Reset {
	// ָ��������working tree��repository�ع�
	public static void resetHard(int n) {
		try {
			if (BranchControl.logReset(n) == 1) {
				BranchControl.treeReset(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��commit��working tree��repository�ع�
	public static void resetHard(String commit) {
		try {
			if (BranchControl.logReset(commit) == 1) {
				BranchControl.treeReset(commit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��������repository�ع�
	public static void resetMixed(int n) {
		try {
			BranchControl.logReset(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��commit��repository�ع�
	public static void resetMixed(String commit) {
		try {
			BranchControl.logReset(commit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
