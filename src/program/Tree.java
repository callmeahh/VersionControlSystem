package program;

import java.io.File;

public class Tree extends KeyValueObject {
	private StringBuilder value = new StringBuilder();

	// 通过传入的文件夹路径初始化Blob对象
	public Tree(String filename) {
		File file = new File(filename);
		this.key = generateKey();
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				value.append("blob " + new Blob(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			} else if (f.isDirectory()) {
				value.append("tree " + new Tree(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			}
		}
		record();
	}

	@Override
	protected void record() {
		try {
			ObjectStorage.storeFromString(key, getValue(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String generateKey() {
		try {
			key = GetHashSHA1.getStringHash(getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public String getValue() {
		return value.toString();
	}
}
