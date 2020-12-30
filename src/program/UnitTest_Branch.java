package program;

public class UnitTest_Branch {

	public static void main(String[] args) throws Exception {
		// 1. ��ʼ���ֿ�
		FilepathSetting.InitRepo();
		// 2. ������Ҫ�����·��
		String filepath = "D:\\0.�γ�����\\java\\working tree";
		FilepathSetting.setTargetFilepath(filepath);

		// 3. ��֧����
		new Branch(); // �½�Ĭ�Ϸ�֧
		new Branch("testing"); // �½�testing��֧���ᱨ��ʧ��
		BranchControl.printAllBranches(); // �鿴���з�֧

		// 4. ��һ���ύcommit�������ԡ�������֧������������֧���л���
		new Commit();// �ύcommit
		new Branch("testing"); // commit���½�testing��֧���ɹ�
		new Branch("testing2", true);// �½�testing2��֧���ɹ�
		new Branch("testing2", true); // ����ͬ����֧��ʧ��
		BranchControl.printAllBranches();// �鿴���з�֧
		System.out.println(BranchControl.getCurrentBranch());// �鿴��ǰ��֧

		// 5. �޸��ļ��󣬵ڶ����ύcommit
		new Commit("commit 2");// �ύcommit
		BranchControl.getCurrentBranchLog();// �鿴��ǰ��֧��־
		BranchControl.getAllLog();// �鿴ȫ����־

		// 6. �ع���testing��֧
		BranchControl.switchBranch("testing");
		BranchControl.getCurrentBranchLog();// �鿴��ǰ��֧��־
		BranchControl.getAllLog();// �鿴ȫ����־
	}

}
