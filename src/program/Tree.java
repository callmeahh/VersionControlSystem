package program;

import java.io.File;

public class Tree extends KeyValueObject {
	private StringBuilder content = new StringBuilder();

	public Tree(String filename) throws Exception {
		File file = new File(filename);
		this.key = generateKey();
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				content.append("blob " + new Blob(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			} else if (f.isDirectory()) {
				content.append("tree " + new Tree(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			}
		}
		record();
	}

	public String getValue() {
		return content.toString();
	}

	@Override
	public String toString() {
		return "040000 tree" + getKey();
	}

	@Override
	public void record() {
		// TODO Auto-generated method stub
		try {
			ObjectStorage.storeTree(key, getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String generateKey() {
		// TODO Auto-generated method stub
		try {
			key = GetHashSHA1.getStringHash(getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
}
