package program;

public abstract class KeyValueObject {
	protected String key = null;;

	// �������¼���ļ�
	protected abstract void record();

	// ����key
	protected abstract String generateKey();

	// ��ȡvalue
	public abstract String getValue();

	// ��ȡkey
	public String getKey() {
		return key;
	}
}
