package program;

import java.io.File;
import java.util.ArrayList;

public class BranchControl {
	private static final String MASTER = "master";
	private static final String HEAD = "HEAD";
	private static final String NULLHash = "0000000000000000000000000000000000000000";

	// ��ȡ��֧�б�
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// ��ü�¼��ǰ��֧�����ļ���HEAD
	private static File getCurrentBranchFile() {
		return new File(FilepathSetting.getFilepath() + File.separator + HEAD);
	}

	// ���ص�ǰ��֧
	protected static String getCurrentBranch() {
		try {
			return ObjectStorage.getContent(getCurrentBranchFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ȡ��ǰ��֧ͷָ���ļ���refs.��֧
	private static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡ��ǰ��֧��־�ļ���logs.refs.��֧
	private static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// �������־�ļ���logs.HEAD
	private static File getAllLogFile() {
		return new File(FilepathSetting.getAllLogFilepath() + File.separator + HEAD);
	}

	// 1��ȡ��ǰ��֧ͷָ�룬��ǰ��֧commit
	protected static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getContent(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	//1��ȡָ����֧ͷָ��
	private static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//��ȡ��ǰcommit��parent����
	private static int parentNum(String commit) throws Exception {
		int count = 0;
		while(ObjectStorage.searchValue(commit).charAt(46) == 'p') {
			String par = ObjectStorage.searchValue(commit).substring(53, 93);
			commit = par;
			count++;
		}
		return count;
	}
	
	//��ȡ��ǰcommitָ��������parent
	private static String getBefore(int n) throws Exception {
		String commit = getHead();
		String par = ObjectStorage.searchValue(commit).substring(53, 93);
		n--;
		for(int i = 0; i < n; i++) {
			commit = par;
			par = ObjectStorage.searchValue(commit).substring(53, 93);
		}
		return par;
	}
	
	//�ж�commit�Ƿ���ڵ�ǰ��֧��¼��
	private static boolean commitExists(String commit) {
		String content = getCurrentBranchLog();
		if (content.contains(commit)) {
			return true;
		}
		return false;
	}

	// 1�õ���ǰ��֧��־������
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

	// 1�õ�����־����
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

	// ���·�֧ͷָ���ļ��ͷ�֧��־�ļ���ÿһ��commit��
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

	// TODO����������֧
	public static String renameBranch(String branchName, String newBranchName) {
		return newBranchName;
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
				System.out.println("���������������շ�֧��");
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
			//��ǰ��֧��commit���л���֧��commit����ͬʱ�Ŷ�working tree����
			if(!getHead().equals(getHead(branchName))){
				try {
					ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
					String tree = ObjectStorage.searchValue(getHead(branchName)).substring(5, 45);
					ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//����log�ļ�
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

	// ��ӡ���з�֧
	public static String printAllBranches() {
		StringBuilder s = new StringBuilder();
		for (String f : getAllBranchName()) {
			s.append(f + "\n");
		}
		System.out.println(s.toString());
		return s.toString();
	}
	
	//�ع�n�Σ��޸�repository
	protected static int logReset(int n) throws Exception{
		String key = getHead();
		if(n > parentNum(key) || !commitExists(getBefore(n))) {
			System.out.println("�÷�֧�����ڴ�commit����������ȷ�Ļع�����");
			return 0;
		}
		else {
			String message = "Reset: " + "moving to HEAD~" + n;
			String writeline = key + " " + getBefore(n) + " " + message + "\n";
			//д��logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			//д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			//�޸�refs.��֧
			ObjectStorage.updateFile(getBefore(n), getHeadFile(), false);
			return 1;
		}
	}
	
	//�ع���ָ��commit���޸�repository
	protected static int logReset(String commit) throws Exception {
		String key = getHead();
		String before = ObjectStorage.getFullName(commit);
		if(ObjectStorage.getFullName(commit) != null) {
			String message = "Reset: " + "moving to " + commit;
			String writeline = key + " " + before + " " + message + "\n";
			//д��logs.HEAD
			ObjectStorage.updateFile(writeline, getAllLogFile(), true);
			//д��logs.refs.��֧
			ObjectStorage.updateFile(writeline, getHeadLogFile(), true);
			//�޸�refs.��֧
			ObjectStorage.updateFile(before, getHeadFile(), false);
			return 1;
		}
		else {
			return 0;
		}
	}
	
	//�ع�n�Σ��޸�working tree
	protected static void treeReset(int n) throws Exception{
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(getBefore(n)).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}
	
	//�ع���ָ��commit���޸�working tree
	protected static void treeReset(String commit) throws Exception {
		String before = ObjectStorage.getFullName(commit);
		ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
		String tree = ObjectStorage.searchValue(before).substring(5, 45);
		ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
	}
}