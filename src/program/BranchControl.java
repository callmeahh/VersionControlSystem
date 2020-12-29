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

	// ��ü�¼��ǰ��֧�����ļ�
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

	// ��ȡ��ǰ��֧ͷָ���ļ�
	private static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡ��ǰ��֧��־�ļ�
	private static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// �������־�ļ�
	private static File getAllLogFile() {
		return new File(FilepathSetting.getAllLogFilepath() + File.separator + HEAD);
	}

	// ��ȡ��ǰ��֧ͷָ��
	protected static String getHead() {
		String str = null;
		try {
			str = ObjectStorage.getContent(getHeadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	//��ȡָ����֧ͷָ��
	private static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	// �õ���ǰ��֧��־������
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

	// �õ�����־����
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
			for (String branch : getAllBranchName()) {
				if(!getHead().equals(getHead(branchName))){
					try {
						ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
						String tree = ObjectStorage.searchValue(getHead(branchName)).substring(5, 45);
						ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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

}
