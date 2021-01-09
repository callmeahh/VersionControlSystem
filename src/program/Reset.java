package program;

public class Reset {
	// ��ȡ��ǰ��֧commit��parent����
	private static int parentNum(String commit) throws Exception {
		int count = 0;
		while (ObjectStorage.searchValue(commit).charAt(46) == 'p') {
			String par = ObjectStorage.searchValue(commit).substring(53, 93);
			commit = par;
			count++;
		}
		return count;
	}

	// ��ȡ��ǰ��֧commitָ��������parent
	private static String getBefore(int n) throws Exception {
		String commit = FileOperation.getHead();
		String par = ObjectStorage.searchValue(commit).substring(53, 93);
		n--;
		for (int i = 0; i < n; i++) {
			commit = par;
			par = ObjectStorage.searchValue(commit).substring(53, 93);
		}
		return par;
	}

	// �ж�commit�Ƿ���logs.refs.��֧�ĵ�ǰ��֧�ļ���������
	private static boolean commitExists(String commit) {
		String content = Log.getCurrentBranchLog();
		if (content.contains(commit)) {
			return true;
		}
		return false;
	}

	// �ع�n�Σ��޸ı��زֿ�
	private static int logReset(int n) throws Exception {
		String key = FileOperation.getHead();
		if (n > parentNum(key) || !commitExists(getBefore(n))) {
			System.out.println("�÷�֧�����ڴ�commit����������ȷ�Ļع�����");
			return 0;
		} else {
			String message = "Reset: " + "moving to HEAD~" + n;
			String writeline = key + " " + getBefore(n) + " " + message + "\n";
			// д��logs.HEAD
			ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
			// д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
			// �޸�refs.��֧
			ObjectStorage.updateFile(getBefore(n), FileOperation.getHeadFile(), false);
			return 1;
		}
	}

	// �ع���ָ��commit���޸ı��زֿ�
	private static int logReset(String commit) throws Exception {
		String key = FileOperation.getHead();
		String before = ObjectStorage.getFullName(commit);
		if (ObjectStorage.getFullName(commit) != null) {
			String message = "Reset: " + "moving to " + commit;
			String writeline = key + " " + before + " " + message + "\n";
			// д��logs.HEAD
			ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
			// д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
			// �޸�refs.��֧
			ObjectStorage.updateFile(before, FileOperation.getHeadFile(), false);
			return 1;
		} else {
			return 0;
		}
	}

	// �ع�n�Σ��޸Ĺ�����
	private static void treeReset(int n) throws Exception {
		String before = FileOperation.getHead();
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// �ع���ָ��commit���޸Ĺ�����
	private static void treeReset(String commit) throws Exception {
		String before = ObjectStorage.getFullName(commit);
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// ָ��������working tree��repository�ع�
	public static void resetHard(int n) {
		try {
			if (logReset(n) == 1) {
				treeReset(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��commit��working tree��repository�ع�
	public static void resetHard(String commit) {
		try {
			if (logReset(commit) == 1) {
				treeReset(commit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��������repository�ع�
	public static void resetMixed(int n) {
		try {
			logReset(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ָ��commit��repository�ع�
	public static void resetMixed(String commit) {
		try {
			logReset(commit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
