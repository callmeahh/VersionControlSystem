package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class blobKey {
	// 设置文件存放路径
	private static String filePar = "E:\\JavaWorkspace\\VersionRepository";

	// 获取路径下所有文件的地址
	private static File[] getFileList() {
		File dir = new File(filePar);
		return dir.listFiles();
	}

	// 字符串匹配
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
			return index; // 找到一个匹配值，返回该下标
		else
			return -1; // 找到其他匹配值，返回-1
	}

	// 将blob类型文件存入本地
	public static void recordBlob(String filename) throws Exception {
		File file = new File(filename);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		String fileHash = GetHashSHA1.getFileHash(file);
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

	// 将tree类型文件夹存入本地
	public static void recordTree(String filename, String content) throws Exception {
		String outputPath = filePar + File.separator + filename;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		bos.write(content.getBytes());
		bos.close();
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

	// 通过key查找value
	public static void searchValue(String hash) throws Exception {
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
