package program;

import java.io.File;

public class Head{
	private StringBuilder value = new StringBuilder();
	
	//通过调用storeFromString方法直接初始化Head对象，生成Head文件
	public Head(String commitValue) throws Exception{
		ObjectStorage.storeFromString(ObjectStorage.getHead(), commitValue, true);
	}
	
	//通过searchHead方法查找Head文件，将Head文件中的内容赋给value
	public String getValue() throws Exception {
		File fHead = ObjectStorage.searchHead();
		value.append(ObjectStorage.stringFromFile(fHead));
		return value.toString();
	}
}
