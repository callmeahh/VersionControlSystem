package program;


public class Commit extends KeyValueObject{
	private StringBuilder value = new StringBuilder();
	
	public Commit(String treeKey) throws Exception{
		value.append("tree "+treeKey+"\n"+ObjectStorage.getParent());
		recordFromString(value.toString(), false);
	}
	
	public String getValue() {
		return value.toString();
	}
}
