package program;

import java.io.File;

public class Tree extends KeyValueObject {
	private StringBuilder content = new StringBuilder();

	public Tree(String filename) throws Exception {
		File file = new File(filename);
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				content.append("blob " + new Blob(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			} else if (f.isDirectory()) {
				content.append("tree " + new Tree(f.getAbsolutePath()).getKey() + " " + f.getName() + "\n");
			}
		}
		recordTree(content.toString());
	}

	public String getValue() {
		return content.toString();
	}

	@Override
	public String toString() {
		return "040000 tree" + getKey();
	}
}
