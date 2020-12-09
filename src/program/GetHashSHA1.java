package program;

import java.io.InputStream;
import java.security.MessageDigest;

public class GetHashSHA1 {
	// ���ַ���ͨ��SHA-1������ϣ
	public static String getFileHash(InputStream is) throws Exception {
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		int numRead = 0;
		do {
			numRead = is.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		return bytesToHexString(complete.digest());
	}

	// ���ַ���ͨ��SHA-1������ϣ
	public static String getStringHash(String s) throws Exception {
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		complete.update(s.getBytes());
		return bytesToHexString(complete.digest());
	}

	// ����������תΪ�ַ���
	private static String bytesToHexString(byte[] b) {
		String hashString = "";
		for (int j = 0; j < b.length; j++) {
			hashString += Integer.toString(b[j] & 0xFF, 16);
		}
		return hashString;
	}
}
