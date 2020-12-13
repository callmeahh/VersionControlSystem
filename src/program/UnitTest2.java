package program;

public class UnitTest2 {
	public static void main(String[] args) throws Exception {
		// 创建Tree对象
		Tree tree = new Tree("D:\\0.课程资料\\java\\新建文件夹");
		//创建Commit对象
		Commit commit = new Commit(tree.getKey());
		//创建或更新Head对象
		System.out.println(commit.getValue());
		Head head = new Head(commit.getValue());
		System.out.println(head.getValue());
//		// 通过存在的哈希值查找value
//		String s1 = "7e028216eed7b4ca24904db730c8d29dc5137d83";
//		ObjectStorage.searchValue(s1);
//		// 通过不存在的哈希值查找value
//		String s2 = "abcdf";
//		ObjectStorage.searchValue(s2);
	}
}
