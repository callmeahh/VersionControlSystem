package program;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		// ����Blob����
		new Blob("D:\\0.�γ�����\\java\\�½��ļ���\\1.txt");
		// ����Tree����
		new Tree("D:\\0.�γ�����\\java\\�½��ļ���");
		// ͨ�����ڵĹ�ϣֵ����value
		String s1 = "7e028216eed7b4ca24904db730c8d29dc5137d83";
		ObjectStorage.searchValue(s1);
		// ͨ�������ڵĹ�ϣֵ����value
		String s2 = "abcdf";
		ObjectStorage.searchValue(s2);
	}
}
