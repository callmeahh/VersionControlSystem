package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ObjectStorage {
	// 文件存放路径
	private static String filePath = "E:\\JavaWorkspace\\VersionRepository";

	// 将文件类型存入本地
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
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

	// 将字符串类型存入本地
	public static void storeFromString(String key, String value, boolean append) throws Exception {
		String outputPath = filePath + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath, append));
		bos.write(value.getBytes());
		bos.close();
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

	// 获取路径下所有文件的地址
	public static File[] getFileList() {
		File dir = new File(filePath);
		return dir.listFiles();
	}

	// 字符串匹配（用于获取通过部分key寻找value）
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
		return n == 1 ? index : -1; // 找到一个匹配返回其下标，否则返回-1
	}

	// 通过key查找value
	public static String searchValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			String value = new String(getString(fl[flag]).getBytes(), "UTF-8");
			System.out.println(value);
			return value;
		} else {
			System.out.println("输入的哈希值不正确，请重新输入！");
			return null;
		}
	}

	// 获取文件中的内容
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

	// 设置文件保存路径
	public static void setFilePath(String filePath) {
		ObjectStorage.filePath = filePath;
	}

	// 获取当前文件保存路径
	public static String getFilepath() {
		return filePath;
	}
}
