package program;

import java.io.File;

public abstract class KeyValueObject {
	private String key;

	// ����Blob�����key���ڱ��زֿ������ļ�
	public void recordFromFile(File file) throws Exception {
		key = GetHashSHA1.getFileHash(file);
		ObjectStorage.storeFromFile(key, file);
	}

	// ����Tree�����key���ڱ��زֿ������ļ�
	public void recordFromString(String content, boolean append) throws Exception {
		key = GetHashSHA1.getStringHash(content);
		ObjectStorage.storeFromString(key, content, append);
	}

	public String getKey() {
		return key;
	}
}
