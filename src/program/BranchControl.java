package program;

import java.io.File;
import java.util.ArrayList;

public class BranchControl {
	private static final String MASTER = "master";
	private static final String HEAD = "HEAD";
	private static final String NULLHash = "0000000000000000000000000000000000000000";

	// ����refs.��֧����ȡ��֧���б�
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// ��ȡHEAD�ļ�
	private static File getCurrentBranchFile() {
		return new File(FilepathSetting.getFilepath() + File.separator + HEAD);
	}

	// ͨ��HEAD�ļ�����ȡ��ǰ��֧��
	protected static String getCurrentBranch() {
		try {
			return ObjectStorage.getContent(getCurrentBranchFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ȡrefs.��֧�ĵ�ǰ��֧�ļ�
	private static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡlogs.refs.��֧�ĵ�ǰ��֧�ļ�
	private static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡlogs.HEAD�ļ�
	private static File getAllLogFile() {
		return new File(FilepathSetting.getAllLogFilepath() + File.separator + HEAD);
	}

	// ͨ��refs.��֧�ĵ�ǰ��֧�ļ�����ȡ��ǰ��֧commit��key
	protected static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getContent(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// ͨ��refs.��֧��ָ����֧�ļ�����ȡָ����֧commit��key
	private static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

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
		String commit = getHead();
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
		String content = getCurrentBranchLog();
		if (content.contains(commit)) {
			return true;
		}
		return false;
	}

	// ��ȡlogs.refs.��֧�ĵ�ǰ��֧�ļ�������
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

	// ��ȡlogs.HEAD�ļ�������
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

	// ��key��message����logs.refs.��֧�ĵ�ǰ��֧�ļ���logs.HEAD�ļ���refs.��֧�ĵ�ǰ��֧�ļ�
	protected static void updateKey(String key, String message) {
		String parent;
		if (getHead().equals("null")) {
			parent = NULLHash;
		} else {
			parent = getHead();
		}
		String writeline = parent + " " + key + " Commit: " + message + "\n";
		try {
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true); // HEADLog�ļ�Ϊ׷��д��
			ObjectStorage.updateFile(writeline, getAllLogFile(), true); // ÿһ��commit�����������־�ļ�
			ObjectStorage.updateFile(key, getHeadFile(), false); // HEAD�ļ�Ϊ����д��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ж�b1�Ƿ�Ϊ��ǰ��֧��
	private static boolean isBranch(String b1) {
		String b = getCurrentBranch();
		if (b1.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	// ��ʼ����֧���½���֧ʱ��
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
			System.out.println("�޷�����������֧");
			return false;
		} else {
			if (getHead().equals("null")) {
				System.out.println("��������������շ�֧��");
				return false;
			} else {
				String key = getHead();
				String message = "branch: Created from " + getCurrentBranch();
				String writeline = NULLHash + " " + key + " " + message + "\n";
				try {
					ObjectStorage.updateFile(writeline,
							new File(FilepathSetting.getLogFilepath() + File.separator + branchName), true); // HEADLog�ļ�Ϊ׷��д��
					ObjectStorage.updateFile(key,
							new File(FilepathSetting.getHeadFilepath() + File.separator + branchName), false); // HEAD�ļ�Ϊ����д��
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
	}

	// �л���֧
	public static String switchBranch(String branchName) {
		if (getCurrentBranch().equals(branchName)) {
			return branchName;
		} else if (!getAllBranchName().contains(branchName)) {
			System.out.println("�����ڸ÷�֧");
			return getCurrentBranch();
		} else {
			// ��ǰ��֧��commit���л���֧��commit����ͬʱ�Ŷ�working tree����
			if (!getHead().equals(getHead(branchName))) {
				try {
					ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
					String tree = ObjectStorage.searchValue(getHead(branchName)).substring(5, 45);
					ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// ����log�ļ�
			for (String branch : getAllBranchName()) {
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

	// ��������֧
	public static String renameBranch(String branchName, String newBranchName) {
		ArrayList<String> arr = getAllBranchName();
		int exist = 0;
		File f1 = null;
		File f2 = null;
		// �жϷ�֧�Ƿ����
		for (int i = 0; i < arr.size(); i++) {
			if (branchName.equals(arr.get(i))) {
				exist = 1;
				f1 = new File(FilepathSetting.getLogFilepath() + File.separator + branchName);
				f2 = new File(FilepathSetting.getHeadFilepath() + File.separator + branchName);
				break;
			}
		}
		if (exist == 0) {
			System.out.println("�÷�֧�����ڣ�������ʧ��");
		}
		// �ж��������Ƿ�����
		for (int i = 0; i < arr.size(); i++) {
			if (newBranchName.equals(arr.get(i))) {
				exist = -1;
				System.out.println("�����������з�֧��������������ʧ��");
				break;
			}
		}
		// ����֧������������
		if (exist == 1) {
			// ��Ϊ��ǰ��֧
			if (isBranch(branchName)) {
				String message = "Branch: renamed " + branchName + " to " + newBranchName;
				String key = getHead();
				String writeline = key + " " + key + " " + message + "\n";
				try {
					// ��HEAD
					ObjectStorage.updateFile(newBranchName, getCurrentBranchFile(), false);
					// ��logs.HEAD
					ObjectStorage.updateFile(writeline, getAllLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������refs.��֧
				f2.renameTo(new File(f2.getParent() + File.separator + newBranchName));
				// ������logs.refs.��֧
				f1.renameTo(new File(f1.getParent() + File.separator + newBranchName));
				// ��logs.refs.��֧
				try {
					ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return newBranchName;
	}

	// ��ӡ���з�֧
	public static String printAllBranches() {
		StringBuilder s = new StringBuilder();
		for (String f : getAllBranchName()) {
			s.append(f + "\n");
		}
		System.out.println(s.toString());
		return s.toString();
	}

	// �ع�n�Σ��޸ı��زֿ�
	protected static int logReset(int n) throws Exception {
		String key = getHead();
		if (n > parentNum(key) || !commitExists(getBefore(n))) {
			System.out.println("�÷�֧�����ڴ�commit����������ȷ�Ļع�����");
			return 0;
		} else {
			String message = "Reset: " + "moving to HEAD~" + n;
			String writeline = key + " " + getBefore(n) + " " + message + "\n";
			// д��logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			// д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			// �޸�refs.��֧
			ObjectStorage.updateFile(getBefore(n), getHeadFile(), false);
			return 1;
		}
	}

	// �ع���ָ��commit���޸ı��زֿ�
	protected static int logReset(String commit) throws Exception {
		String key = getHead();
		String before = ObjectStorage.getFullName(commit);
		if (ObjectStorage.getFullName(commit) != null) {
			String message = "Reset: " + "moving to " + commit;
			String writeline = key + " " + before + " " + message + "\n";
			// д��logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			// д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			// �޸�refs.��֧
			ObjectStorage.updateFile(before, getHeadFile(), false);
			return 1;
		} else {
			return 0;
		}
	}

	// �ع�n�Σ��޸Ĺ�����
	protected static void treeReset(int n) throws Exception {
		String before = getHead();
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}

	// �ع���ָ��commit���޸Ĺ�����
	protected static void treeReset(String commit) throws Exception {
		String before = ObjectStorage.getFullName(commit);
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}
}
