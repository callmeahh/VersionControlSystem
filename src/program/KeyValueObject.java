package program;

import java.io.File;

public abstract class KeyValueObject {
	private String key;

	// ����Blob�����key���ڱ��زֿ������ļ�
	public void recordBlob(File file) throws Exception {
		key = GetHashSHA1.getFileHash(file);
		ObjectStorage.storeBlob(key, file);
	}

	// ����Tree�����key���ڱ��زֿ������ļ�
	public void recordTree(String content) throws Exception {
		key = GetHashSHA1.getStringHash(content);
		ObjectStorage.storeTree(key, content);
	}

	public String getKey() {
		return key;
	}
}
