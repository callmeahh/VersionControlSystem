package program;

import java.io.File;

class FilepathSetting {
	// ���زֿ��ļ����·��
	private static String filepath = "D:\\0.�γ�����\\java\\commit history";
	private static final String objectFilepath = "objects";
	private static final String headFilepath = "refs";
	private static final String logFilepath = "logs";
	private static final String info = "Info";
	private static String targetFilepath;

	// ���ñ��زֿ��ļ�����·��
	public static void setFilepath(String filepath) {
		FilepathSetting.filepath = filepath;
	}

	// ��ȡ���زֿ��ļ��У����logs��objects��refs�ļ��к�HEAD��Info�ļ���HEAD�ļ���¼��ǰ��֧��Info�ļ���¼������·��
	public static File getFilepath() {
		return new File(filepath);
	}

	// ��ȡobjects�ļ��У����blob��tree��commit�ļ�
	protected static File getObjectFilepath() {
		return new File(filepath + File.separator + objectFilepath);
	}

	// ��ȡrefs�ļ��У���Ÿ���֧�ļ�����¼����֧��ǰcommit��key
	protected static File getHeadFilepath() {
		return new File(filepath + File.separator + headFilepath);
	}

	// ��ȡlogs�ļ��У����refs�ļ��к�HEAD�ļ���HEAD�ļ���¼������ʷ��¼
	protected static File getAllLogFilepath() {
		return new File(filepath + File.separator + logFilepath);
	}

	// ��ȡlogs.refs�ļ��У���Ÿ���֧�ļ�����¼����֧��ʷ��¼
	protected static File getLogFilepath() {
		return new File(filepath + File.separator + logFilepath + File.separator + headFilepath);
	}

	// ��ʼ��Ŀ���ļ��У�����objects�ļ��У�refs�ļ��У�logs�ļ��У�logs.refs�ļ���
	protected static void InitRepo() {
		File[] dirs = new File[] { getFilepath(), getObjectFilepath(), getHeadFilepath(), getAllLogFilepath(),
				getLogFilepath() };
		for (File f : dirs) {
			f.mkdirs();
		}
	}

	// ��ȡ�������ļ�·��
	public static String getTargetFilepath() {
		try {
			return ObjectStorage.getContent(new File(filepath + File.separator + info));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFilepath;
	}

	// ���ù������ļ�·��
	public static void setTargetFilepath(String targetFilepath) {
		FilepathSetting.targetFilepath = targetFilepath;
		try {
			ObjectStorage.storeFromString(info, targetFilepath, filepath, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
