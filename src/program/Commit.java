package program;

public class Commit extends KeyValueObject {
	private StringBuilder value = new StringBuilder();

	// 创建默认commit对象
	public Commit() {
		this(new Tree(FilepathSetting.getTargetFilepath()), null);
	}

	// 传入message创建commit对象
	public Commit(String message) {
		this(new Tree(FilepathSetting.getTargetFilepath()), message);
	}

	// 通过tree对象和commit注释创建commit对象
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
