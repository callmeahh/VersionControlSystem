package program;

import java.io.File;

public class Tree extends KeyValueObject {
	private StringBuilder value = new StringBuilder();

	public Tree(String filename) throws Exception {
		File file = new File(filename);
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				value.append("blob " + new Blob(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			} else if (f.isDirectory()) {
				value.append("tree " + new Tree(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			}
		}
		recordFromString(value.toString(), false);
	}

	public String getValue() {
		return value.toString();
	}
}
