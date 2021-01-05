package program;

public class Ziptest {
	public static void main(String[] args) {
		System.setProperty("user.dir", "E:\\JavaWorkspace\\testdir2");
		FilepathSetting.InitRepo();
//		new Blob("E:\\JavaWorkspace\\helloworld\\C”Ô—‘±‡≥Ã¡∑œ∞Ã‚.md");
		try {
			String tree = "e3feb4c0519899b9c3eba522fa362786447d3ab7";
//			System.out.println(ObjectStorage.formatValue(tree)[1][1]);
//			System.out.println(FilepathSetting.getTargetFilepath());
			ObjectStorage.restoreFiles(ObjectStorage.formatValue(tree), FilepathSetting.getTargetFilepath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
