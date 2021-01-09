package program;

public class Commit extends KeyValueObject {
	private StringBuilder value = new StringBuilder();

	// ����Ĭ��commit����
	public Commit() {
		this(new Tree(FilepathSetting.getTargetFilepath()), null);
	}

	// ����message����commit����
	public Commit(String message) {
		this(new Tree(FilepathSetting.getTargetFilepath()), message);
	}

	// ͨ��tree�����commitע�ʹ���commit����
	private Commit(Tree tree, String message) {
		value.append("tree " + tree.getKey() + "\n");
		if (!FileOperation.getHead().equals("null")) {
			value.append("parent " + FileOperation.getHead() + "\n");
		}
		value.append(message);
		this.key = generateKey();
		record();
		FileOperation.updateKey(key, message);
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
