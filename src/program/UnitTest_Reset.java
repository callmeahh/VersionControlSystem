package program;

public class UnitTest_Reset {

	public static void main(String[] args) throws Exception {
		// 1. 初始化仓库
		FilepathSetting.InitRepo();
		// 2. 设置需要管理的路径
		String filepath = "D:\\0.课程资料\\java\\working tree";
		FilepathSetting.setTargetFilepath(filepath);

		// 3. 分支操作
		new Branch(); // 新建默认分支

		// 4. 第一次提交commit
		new Commit();// 提交commit

		// 5. 修改文件后，第二次提交commit
		new Commit("commit 2");// 提交commit

		// 6. 创建切换分支，修改文件后，第三次提交commit
		new Branch("testing"); // 新建testing分支
		BranchControl.switchBranch("testing");
		new Commit("commit 3");// 提交commit
		System.out.println(BranchControl.getHead());//查看当前分支的commit的key，refs.分支
		System.out.println();
		BranchControl.getCurrentBranchLog();// 查看当前分支日志logs.refs.分支
		System.out.println();
		BranchControl.getAllLog();// 查看全部日志logs.HEAD

		// 7. working tree和repository回滚1次
		Reset.resetHard(1);
		System.out.println(BranchControl.getHead());//查看当前分支的commit的key，refs.分支
		System.out.println();
		BranchControl.getCurrentBranchLog();// 查看当前分支日志logs.refs.分支
		System.out.println();
		BranchControl.getAllLog();// 查看全部日志logs.HEAD
		
		
		// 8. working tree和repository回滚1次（超出分支当前commit个数）
		Reset.resetHard(1);
		
		// 9. repository回滚到4d765（跨分支向前回滚）
		Reset.resetMixed("4d765");
		System.out.println(BranchControl.getHead());//查看当前分支的commit的key，refs.分支
		System.out.println();
		BranchControl.getCurrentBranchLog();// 查看当前分支日志logs.refs.分支
		System.out.println();
		BranchControl.getAllLog();// 查看全部日志logs.HEAD
		
		// 10. working tree和repository回滚到7089c（跨分支向后回滚）
		Reset.resetHard("7089c");
	}

}
