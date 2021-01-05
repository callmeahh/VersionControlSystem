package program;

import java.io.File;

public class Blob extends KeyValueObject {
	private File file;

	// 通过传入的文件路径初始化Blob对象
	public Blob(String filename) {
		File file = new File(filename);
		this.file = file;
		this.key = generateKey();
		record();
	}

	@Override
	protected void record() {
		try {
			ObjectStorage.storeFromFile(getKey(), file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String generateKey() {
		try {
			key = GetHashSHA1.getFileHash(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public String getValue() {
		try {
			return ObjectStorage.getContent(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
