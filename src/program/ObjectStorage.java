package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ObjectStorage {
	// �ļ����·��
	private static String filePath = "E:\\JavaWorkspace\\VersionRepository";

	// ���ļ����ʹ��뱾��
	public static void storeFromFile(String key, File file) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int len;
		String outputPath = filePath + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		bis.close();
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ���ַ������ʹ��뱾��
	public static void storeFromString(String key, String value, boolean append) throws Exception {
		String outputPath = filePath + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath, append));
		bos.write(value.getBytes());
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ��ȡ·���������ļ��ĵ�ַ
	public static File[] getFileList() {
		File dir = new File(filePath);
		return dir.listFiles();
	}

	// �ַ���ƥ�䣨���ڻ�ȡͨ������keyѰ��value��
	private static int findSubstring(File[] fl, String s2) {
		int n = 0;
		int index = -1;
		for (int j = 0; j < fl.length; j++) {
			String s = fl[j].getName();
			for (int i = 0; i < s2.length(); i++) {
				if (s.charAt(i) != s2.charAt(i)) {
					break;
				}
				if (i == s2.length() - 1) {
					index = j;
					n++;
				}
			}
		}
		return n == 1 ? index : -1; // �ҵ�һ��ƥ�䷵�����±꣬���򷵻�-1
	}

	// ͨ��key����value
	public static String searchValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			String value = new String(getString(fl[flag]).getBytes(), "UTF-8");
			System.out.println(value);
			return value;
		} else {
			System.out.println("����Ĺ�ϣֵ����ȷ�����������룡");
			return null;
		}
	}

	// ��ȡ�ļ��е�����
	protected static String getString(File file) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String str = "";
		StringBuilder sb = new StringBuilder();
		sb.append(str = bufferedReader.readLine());
		while ((str = bufferedReader.readLine()) != null) {
			sb.append("\n" + str);
		}
		bufferedReader.close();
		return sb.toString();
	}

	// �����ļ�����·��
	public static void setFilePath(String filePath) {
		ObjectStorage.filePath = filePath;
	}

	// ��ȡ��ǰ�ļ�����·��
	public static String getFilepath() {
		return filePath;
	}
}
