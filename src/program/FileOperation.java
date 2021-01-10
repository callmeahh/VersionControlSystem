package program;

import java.io.File;
import java.util.ArrayList;

public class FileOperation {
	private static final String HEAD = "HEAD";
	protected static final String NULLHash = "0000000000000000000000000000000000000000";

	// ����refs.��֧����ȡ��֧���б�
	protected static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// ��ȡHEAD�ļ�
	protected static File getCurrentBranchFile() {
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
	protected static File getHeadFile() {
		return new File(FilepathSetting.getHeadFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡlogs.refs.��֧�ĵ�ǰ��֧�ļ�
	protected static File getHeadLogFile() {
		return new File(FilepathSetting.getLogFilepath() + File.separator + getCurrentBranch());
	}

	// ��ȡlogs.HEAD�ļ�
	protected static File getAllLogFile() {
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
	protected static String getHead(String branchName) {
		String str = null;
		try {
			str = ObjectStorage.getContent(new File(FilepathSetting.getHeadFilepath() + File.separator + branchName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
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

}
