package program;

public class Log {
	// ��ȡlogs.refs.��֧�ĵ�ǰ��֧�ļ�������
	public static String getCurrentBranchLog() {
		String content = null;
		try {
			content = ObjectStorage.getContent(FileOperation.getHeadLogFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(content);
		return content;
	}

	// ��ȡlogs.HEAD�ļ�������
	public static String getAllLog() {
		String content = null;
		try {
			content = ObjectStorage.getContent(FileOperation.getAllLogFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(content);
		return content;
	}

}
