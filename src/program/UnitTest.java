package program;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		// ����Blob����
		new Blob("E:\\JavaWorkspace\\123456789.txt");
		// ����Tree����
		new Tree("E:\\JavaWorkspace\\helloworld\\testdir2");
		// ͨ�����ڵĹ�ϣֵ����value
		String s1 = "da39a3ee5e6b4b0d";
		ObjectStorage.searchValue(s1);
		// ͨ�������ڵĹ�ϣֵ����value
		String s2 = "abcdf";
		ObjectStorage.searchValue(s2);
	}
}
