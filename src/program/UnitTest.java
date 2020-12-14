package program;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		// 创建Blob对象
		new Blob("E:\\JavaWorkspace\\123456789.txt");
		// 创建Tree对象
		new Tree("E:\\JavaWorkspace\\helloworld\\testdir2");
		// 通过存在的哈希值查找value
		String s1 = "da39a3ee5e6b4b0d";
		ObjectStorage.searchValue(s1);
		// 通过不存在的哈希值查找value
		String s2 = "abcdf";
		ObjectStorage.searchValue(s2);
	}
}
