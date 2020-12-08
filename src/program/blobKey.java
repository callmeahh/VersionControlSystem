package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class blobKey {
	private static String filePar = "E:\\JavaWorkspace\\VersionRepository";

	private static File[] getFileList() {
		File dir = new File(filePar);
		return dir.listFiles();
	}

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
			return index;
		else
			return -1;
	}

	public static void recordBlob(String filename) throws Exception {
		File file = new File(filename);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		String fileHash = GetHashSHA1.getFileHash(bis);
		int len;
		String outputPath = filePar + File.separator + fileHash;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		bis.close();
		bos.close();
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

	public static void getValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fl[flag]));
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				System.out.printf(str, "UTF-8");
			}
			bufferedReader.close();
		} else {
			System.out.println("输入的哈希值不正确，请重新输入！");
		}
	}
}
