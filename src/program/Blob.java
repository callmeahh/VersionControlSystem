package program;

import java.io.File;

public class Blob extends KeyValueObject {
	public Blob(String filename) throws Exception {
		File file = new File(filename);
		recordBlob(file);
	}

	@Override
	public String toString() {
		return "100644 blob " + getKey();
	}
}
