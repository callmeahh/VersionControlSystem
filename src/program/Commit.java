package program;

public class Commit extends KeyValueObject {
	private StringBuilder value = new StringBuilder();

	// ͨ��Tree���󴴽�commit����
	public Commit(Tree tree) {
		this(tree, null);
	}

	// ͨ��tree�����commitע�ʹ���commit����
	public Commit(Tree tree, String message) {
		value.append("tree " + tree.getKey() + "\n");
		if (Head.init()) {
			value.append("parent " + Head.getHead() + "\n");
		}
		value.append(message);
		this.key = generateKey();
		record();
		Head.updateKey(key, message);
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
