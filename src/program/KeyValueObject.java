package program;

import java.io.File;

public abstract class KeyValueObject {
	private String key;

	// 生成Blob对象的key并在本地仓库生成文件
	public void recordFromFile(File file) throws Exception {
		key = GetHashSHA1.getFileHash(file);
		ObjectStorage.storeFromFile(key, file);
	}

	// 生成Tree对象的key并在本地仓库生成文件
	public void recordFromString(String content, boolean append) throws Exception {
		key = GetHashSHA1.getStringHash(content);
		ObjectStorage.storeFromString(key, content, append);
	}

	public String getKey() {
		return key;
	}
}
