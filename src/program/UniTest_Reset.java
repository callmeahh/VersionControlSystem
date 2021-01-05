package program;

public class UnitTest_Reset {

	public static void main(String[] args) throws Exception {
		// 1. ��ʼ���ֿ�
		FilepathSetting.InitRepo();
		// 2. ������Ҫ�����·��
		String filepath = "D:\\0.�γ�����\\java\\working tree";
		FilepathSetting.setTargetFilepath(filepath);

		// 3. ��֧����
		new Branch(); // �½�Ĭ�Ϸ�֧

		// 4. ��һ���ύcommit
		new Commit();// �ύcommit

		// 5. �޸��ļ��󣬵ڶ����ύcommit
		new Commit("commit 2");// �ύcommit

		// 6. �����л���֧���޸��ļ��󣬵������ύcommit
		new Branch("testing"); // �½�testing��֧
		BranchControl.switchBranch("testing");
		new Commit("commit 3");// �ύcommit
		System.out.println(BranchControl.getHead());//�鿴��ǰ��֧��commit��key��refs.��֧
		System.out.println();
		BranchControl.getCurrentBranchLog();// �鿴��ǰ��֧��־logs.refs.��֧
		System.out.println();
		BranchControl.getAllLog();// �鿴ȫ����־logs.HEAD

		// 7. working tree��repository�ع�1��
		Reset.resetHard(1);
		System.out.println(BranchControl.getHead());//�鿴��ǰ��֧��commit��key��refs.��֧
		System.out.println();
		BranchControl.getCurrentBranchLog();// �鿴��ǰ��֧��־logs.refs.��֧
		System.out.println();
		BranchControl.getAllLog();// �鿴ȫ����־logs.HEAD
		
		
		// 8. working tree��repository�ع�1�Σ�������֧��ǰcommit������
		Reset.resetHard(1);
		
		// 9. repository�ع���4d765�����֧��ǰ�ع���
		Reset.resetMixed("4d765");
		System.out.println(BranchControl.getHead());//�鿴��ǰ��֧��commit��key��refs.��֧
		System.out.println();
		BranchControl.getCurrentBranchLog();// �鿴��ǰ��֧��־logs.refs.��֧
		System.out.println();
		BranchControl.getAllLog();// �鿴ȫ����־logs.HEAD
		
		// 10. working tree��repository�ع���7089c�����֧���ع���
		Reset.resetHard("7089c");
		
		// 11. ��������֧
		//���벻���ڵķ�֧testʱ��������ʧ��
		BranchControl.renameBranch("test", "test1");
		//�����Ѵ��ڵķ�֧masterʱ��������ʧ��
		BranchControl.renameBranch("testing", "master");
		//������ڵķ�֧testing�Ͳ����ڵ�������test1ʱ���������ɹ�
		BranchControl.renameBranch("testing", "test1");
	}

}