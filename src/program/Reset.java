package program;

public class Reset {
	//ָ��������working tree��repository�ع�
	public static void resetHard(int n) throws Exception {
		if(BranchControl.logReset(n) == 1) {
			BranchControl.treeReset(n);
		}
	}
	//ָ��commit��working tree��repository�ع�
	public static void resetHard(String commit) throws Exception {
		if(BranchControl.logReset(commit) == 1) {
			BranchControl.treeReset(commit);
		}
	}
	//ָ��������working tree�ع�
	public static void resetMixed(int n) throws Exception {
		BranchControl.logReset(n);
	}
	//ָ��commit��working tree�ع�
	public static void resetMixed(String commit) throws Exception {
		BranchControl.logReset(commit);
	}
}
