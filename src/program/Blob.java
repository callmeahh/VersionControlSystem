package program;

import java.io.File;

public class Blob extends KeyValueObject {
	public Blob(String filename) throws Exception {
		File file = new File(filename);
		recordFromFile(file);
	}
}
