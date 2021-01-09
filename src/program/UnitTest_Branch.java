package program;

public class UnitTest_Branch {

	public static void main(String[] args) throws Exception {
		// 1. 初始化仓库
		FilepathSetting.InitRepo();
		// 2. 设置需要管理的路径
		String filepath = "D:\\0.课程资料\\java\\working tree";
		FilepathSetting.setTargetFilepath(filepath);

		// 3. 分支操作
		new Branch(); // 新建默认分支
		new Branch("testing"); // 新建testing分支，会报错失败
		Branch.printAllBranches(); // 查看所有分支

		// 4. 第一次提交commit，并测试“创建分支”、“创建分支并切换”
		new Commit();// 提交commit
		new Branch("testing"); // commit后新建testing分支，成功
		new Branch("testing2", true);// 新建testing2分支，成功
		new Branch("testing2", true); // 创建同名分支，失败
		Branch.printAllBranches();// 查看所有分支
		System.out.println(FileOperation.getCurrentBranch());// 查看当前分支

		// 5. 修改文件后，第二次提交commit
		new Commit("commit 2");// 提交commit
		Log.getCurrentBranchLog();// 查看当前分支日志
		Log.getAllLog();// 查看全部日志

		// 6. 回滚到testing分支
		Branch.switchBranch("testing");
		Log.getCurrentBranchLog();// 查看当前分支日志
		Log.getAllLog();// 查看全部日志

	}

}
