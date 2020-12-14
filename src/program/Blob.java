package program;

import java.io.File;

public class Blob extends KeyValueObject {
	private File file;

	public Blob(String filename) throws Exception {
		File file = new File(filename);
		this.file = file;
		this.key = generateKey();
		record();
	}
	@Override
	public void record() {
		// TODO Auto-generated method stub
		try {
			ObjectStorage.storeBlob(getKey(), file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String generateKey() {
		try {
			key = GetHashSHA1.getFileHash(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
}
