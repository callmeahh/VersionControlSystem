package program;

public class UnitTest2 {
	public static void main(String[] args) throws Exception {
		// ����Tree����
		Tree tree = new Tree("D:\\0.�γ�����\\java\\�½��ļ���");
		//����Commit����
		Commit commit = new Commit(tree.getKey());
		//���������Head����
		System.out.println(commit.getValue());
		Head head = new Head(commit.getValue());
		System.out.println(head.getValue());
//		// ͨ�����ڵĹ�ϣֵ����value
//		String s1 = "7e028216eed7b4ca24904db730c8d29dc5137d83";
//		ObjectStorage.searchValue(s1);
//		// ͨ�������ڵĹ�ϣֵ����value
//		String s2 = "abcdf";
//		ObjectStorage.searchValue(s2);
	}
}
