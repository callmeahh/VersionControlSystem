package program;

import java.io.File;
import java.util.ArrayList;

public class Branch {
	private static final String MASTER = "master";
	private String branchName;

	// �½�Ĭ�Ϸ�֧
	public Branch() {
		this(MASTER, true);
	}

	// �����ƴ�����֧�����л���֧��
	public Branch(String branchName) {
		this(branchName, false);
	}

	// �����ƴ�����֧���л���֧��
	public Branch(String branchName, boolean isSwitch) {
		this.branchName = branchName;
		if (initBranch(branchName)) {
			if (isSwitch) {
				switchBranch(branchName);
			}
		}
	}

	// ����refs.��֧����ȡ��֧���б�
	private static ArrayList<String> getAllBranchName() {
		ArrayList<String> branchNames = new ArrayList<>();
		for (File f : FilepathSetting.getHeadFilepath().listFiles()) {
			branchNames.add(f.getName());
		}
		return branchNames;
	}

	// �ж�b1�Ƿ�Ϊ��ǰ��֧��
	private static boolean isBranch(String b1) {
		String b = FileOperation.getCurrentBranch();
		if (b1.equals(b)) {
			return true;
		} else {
			return false;
		}
	}

	// ��ʼ����֧���½���֧ʱ��
	private static boolean initBranch(String branchName) {
		if (branchName.equals(MASTER)) {
			try {
				new File(FilepathSetting.getLogFilepath() + File.separator + branchName).createNewFile();
				new File(FilepathSetting.getHeadFilepath() + File.separator + branchName).createNewFile();
				ObjectStorage.updateFile(branchName, FileOperation.getCurrentBranchFile(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (getAllBranchName().contains(branchName)) {
			System.out.println("�޷�����������֧");
			return false;
		} else {
			if (FileOperation.getHead().equals("null")) {
				System.out.println("��������������շ�֧��");
				return false;
			} else {
				String key = FileOperation.getHead();
				String message = "branch: Created from " + FileOperation.getCurrentBranch();
				String writeline = FileOperation.NULLHash + " " + key + " " + message + "\n";
				try {
					ObjectStorage.updateFile(writeline,
							new File(FilepathSetting.getLogFilepath() + File.separator + branchName), true); // HEADLog�ļ�Ϊ׷��д��
					ObjectStorage.updateFile(key,
							new File(FilepathSetting.getHeadFilepath() + File.separator + branchName), false); // HEAD�ļ�Ϊ����д��
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
	}

	// �л���֧
	public static String switchBranch(String branchName) {
		if (FileOperation.getCurrentBranch().equals(branchName)) {
			return branchName;
		} else if (!getAllBranchName().contains(branchName)) {
			System.out.println("�����ڸ÷�֧");
			return FileOperation.getCurrentBranch();
		} else {
			// ��ǰ��֧��commit���л���֧��commit����ͬʱ�Ŷ�working tree����
			if (!FileOperation.getHead().equals(FileOperation.getHead(branchName))) {
				try {
					ObjectStorage.deleteDir(FilepathSetting.getTargetFilepath());
					String tree = ObjectStorage.searchValue(FileOperation.getHead(branchName)).substring(5, 45);
					ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// ����log�ļ�
			for (String branch : getAllBranchName()) {
				if (branch.equals(branchName)) {
					String message = "Switch: " + FileOperation.getCurrentBranch() + " -> " + branchName;
					String key = FileOperation.getHead();
					String writeline = key + " " + key + " " + message + "\n";
					try {
						ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
						ObjectStorage.updateFile(branchName, FileOperation.getCurrentBranchFile(), false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return branchName;
				}
			}
		}
		return FileOperation.getCurrentBranch();
	}

	// ��������֧
	public static String renameBranch(String branchName, String newBranchName) {
		ArrayList<String> arr = getAllBranchName();
		int exist = 0;
		File f1 = null;
		File f2 = null;
		// �жϷ�֧�Ƿ����
		for (int i = 0; i < arr.size(); i++) {
			if (branchName.equals(arr.get(i))) {
				exist = 1;
				f1 = new File(FilepathSetting.getLogFilepath() + File.separator + branchName);
				f2 = new File(FilepathSetting.getHeadFilepath() + File.separator + branchName);
				break;
			}
		}
		if (exist == 0) {
			System.out.println("�÷�֧�����ڣ�������ʧ��");
		}
		// �ж��������Ƿ�����
		for (int i = 0; i < arr.size(); i++) {
			if (newBranchName.equals(arr.get(i))) {
				exist = -1;
				System.out.println("�����������з�֧��������������ʧ��");
				break;
			}
		}
		// ����֧������������
		if (exist == 1) {
			// ��Ϊ��ǰ��֧
			if (isBranch(branchName)) {
				String message = "Branch: renamed " + branchName + " to " + newBranchName;
				String key = FileOperation.getHead();
				String writeline = key + " " + key + " " + message + "\n";
				try {
					// ��HEAD
					ObjectStorage.updateFile(newBranchName, FileOperation.getCurrentBranchFile(), false);
					// ��logs.HEAD
					ObjectStorage.updateFile(writeline, FileOperation.getAllLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������refs.��֧
				f2.renameTo(new File(f2.getParent() + File.separator + newBranchName));
				// ������logs.refs.��֧
				f1.renameTo(new File(f1.getParent() + File.separator + newBranchName));
				// ��logs.refs.��֧
				try {
					ObjectStorage.updateFile(writeline, FileOperation.getHeadLogFile(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return newBranchName;
	}

	// ��ӡ���з�֧
	public static String printAllBranches() {
		StringBuilder s = new StringBuilder();
		for (String f : getAllBranchName()) {
			s.append(f + "\n");
		}
		System.out.println(s.toString());
		return s.toString();
	}

	public String getBranchName() {
		return branchName;
	}

}
