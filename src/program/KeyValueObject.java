package program;

public abstract class KeyValueObject {
	protected String key = null;;
	
	public abstract void record();
	public abstract String generateKey();

	public String getKey() {
		return key;
	}
}
