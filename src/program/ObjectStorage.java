package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ObjectStorage {
	// 设置文件存放路径
	private static final String filePar = "E:\\JavaWorkspace\\VersionRepository";

	// 将blob类型文件存入本地
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
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

	// 将tree类型文件夹存入本地
	public static void storeTree(String key, String value) throws Exception {
		String outputPath = filePar + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		bos.write(value.getBytes());
		bos.close();
		System.out.println("执行完毕，文件导出到 " + outputPath);
	}

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

	// 通过key查找value
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
			System.out.println("输入的哈希值不正确，请重新输入！");
			return null;
		}
	}
}
