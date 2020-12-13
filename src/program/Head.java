package program;

import java.io.File;

public class Head{
	private StringBuilder value = new StringBuilder();
	
	//ͨ������storeFromString����ֱ�ӳ�ʼ��Head��������Head�ļ�
	public Head(String commitValue) throws Exception{
		ObjectStorage.storeFromString(ObjectStorage.getHead(), commitValue, true);
	}
	
	//ͨ��searchHead��������Head�ļ�����Head�ļ��е����ݸ���value
	public String getValue() throws Exception {
		File fHead = ObjectStorage.searchHead();
		value.append(ObjectStorage.stringFromFile(fHead));
		return value.toString();
	}
}
