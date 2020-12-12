package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ObjectStorage {
	// �����ļ����·��
	private static final String filePar = "E:\\JavaWorkspace\\VersionRepository";

	// ��blob�����ļ����뱾��
	public static void storeBlob(String key, File file) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int len;
		String outputPath = filePar + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		bis.close();
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ��tree�����ļ��д��뱾��
	public static void storeTree(String key, String value) throws Exception {
		String outputPath = filePar + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		bos.write(value.getBytes());
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ��ȡ·���������ļ��ĵ�ַ
	private static File[] getFileList() {
		File dir = new File(filePar);
		return dir.listFiles();
	}

	// �ַ���ƥ��
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
		if (n == 1)
			return index; // �ҵ�һ��ƥ��ֵ�����ظ��±�
		else
			return -1; // �ҵ�����ƥ��ֵ������-1
	}

	// ͨ��key����value
	public static String searchValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fl[flag]));
			String str = "";
			StringBuilder sb = new StringBuilder();
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str + "\n");
				System.out.println(new String(str.getBytes(), "UTF-8"));
			}
			bufferedReader.close();
			return new String(sb.toString().getBytes(), "UTF-8");
		} else {
			System.out.println("����Ĺ�ϣֵ����ȷ�����������룡");
			return null;
		}
	}
}
