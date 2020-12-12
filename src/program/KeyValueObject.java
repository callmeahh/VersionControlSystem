package program;

import java.io.File;

public abstract class KeyValueObject {
	private String key;

	// 生成Blob对象的key并在本地仓库生成文件
	public void recordBlob(File file) throws Exception {
		key = GetHashSHA1.getFileHash(file);
		ObjectStorage.storeBlob(key, file);
	}

	// 生成Tree对象的key并在本地仓库生成文件
	public void recordTree(String content) throws Exception {
		key = GetHashSHA1.getStringHash(content);
		ObjectStorage.storeTree(key, content);
	}

	public String getKey() {
		return key;
	}
}
