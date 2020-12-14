package program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;

public class GetHashSHA1 {
	// 将字符串通过SHA-1方法哈希
	public static String getFileHash(File file) throws Exception {
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		FileInputStream is = new FileInputStream(file);
		int numRead = 0;
		do {
			numRead = is.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		is.close();
		return bytesToHexString(complete.digest());
	}

	// 将字符串通过SHA-1方法哈希
	public static String getStringHash(String s) throws Exception {
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		complete.update(s.getBytes());
		return bytesToHexString(complete.digest());
	}

	// 将数组类型转为字符串
	private static String bytesToHexString(byte[] b) {
		String hashString = "";
		int n = b.length;
		for (int i = 0; i < n; i++) {
			String append = Integer.toString(b[i] & 0xFF, 16);
			if (append.length() < 2) {
				hashString += "0" + append;
			} else {
				hashString += append;
			}
		}
		return hashString;
	}
}
