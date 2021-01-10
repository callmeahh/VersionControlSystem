package program;

public class Log {
	// 获取logs.refs.分支的当前分支文件的内容
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

	// 获取logs.HEAD文件的内容
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
