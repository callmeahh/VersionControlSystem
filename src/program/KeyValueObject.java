package program;

public abstract class KeyValueObject {
	protected String key = null;;

	// 将对象记录到文件
	protected abstract void record();

	// 生成key
	protected abstract String generateKey();

	// 获取value
	public abstract String getValue();

	// 获取key
	public String getKey() {
		return key;
	}
}
