package program;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		// 创建Blob对象
		new Blob("D:\\0.课程资料\\java\\新建文件夹\\1.txt");
		// 创建Tree对象
		new Tree("D:\\0.课程资料\\java\\新建文件夹");
		// 通过存在的哈希值查找value
		String s1 = "7e028216eed7b4ca24904db730c8d29dc5137d83";
		ObjectStorage.searchValue(s1);
		// 通过不存在的哈希值查找value
		String s2 = "abcdf";
		ObjectStorage.searchValue(s2);
	}
}
