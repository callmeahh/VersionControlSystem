package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ObjectStorage {
	// �����ļ����·��
	private static final String filePar = "D:\\0.�γ�����\\java\\��Ŀ";
	private static final String Head = "Head";

	// ��blob�����ļ����뱾��
	public static void storeFromFile(String key, File file) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int len;
		String outputPath = filePar + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		bis.close();
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ��tree�����ļ��д��뱾��
	public static void storeFromString(String key, String value, boolean append) throws Exception {
		String outputPath = filePar + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath, append));
		bos.write(value.getBytes());
		bos.close();
		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ��ȡ·���������ļ��ĵ�ַ
	public static File[] getFileList() {
		File dir = new File(filePar);
		return dir.listFiles();
	}

	// �ַ���ƥ��
	private static int findSubstring(File[] fl, String s2) {
		int n = 0;
		int index = -1;
		for (int j = 0; j < fl.length; j++) {
			String s = fl[j].getName();
			for (int i = 0; i < s2.length(); i++) {
				if (s.charAt(i) != s2.charAt(i)) {
					break;
				}
				if (i == s2.length() - 1) {
					index = j;
					n++;
				}
			}
		}
		if (n == 1)
			return index; // �ҵ�һ��ƥ��ֵ�����ظ��±�
		else
			return -1; // �ҵ�����ƥ��ֵ������-1
	}

	// ͨ��key����value
	public static String searchValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fl[flag]));
			String str = "";
			StringBuilder sb = new StringBuilder();
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str + "\n");
				System.out.println(new String(str.getBytes(), "UTF-8"));
			}
			bufferedReader.close();
			return new String(sb.toString().getBytes(), "UTF-8");
		} else {
			System.out.println("����Ĺ�ϣֵ����ȷ�����������룡");
			return null;
		}
	}
	
	//����Head�ļ�
	public static File searchHead() throws Exception{
		File[] fl = getFileList();
		for(int i = 0; i < fl.length; i++) {
			if(fl[i].getName().equals(getHead())) {
				return fl[i];
			}
		}
		//û��Head�ļ��򴴽��ļ�
		String path = filePar + File.separator + getHead();
		File f = new File(path);
		f.createNewFile();
		return f;
	}
	
	//��ȡHead�ļ��ĵ�һ��commit��key��Ϊparent
	public static String getParent() throws Exception {
		File fHead = searchHead();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fHead));
		StringBuilder par = new StringBuilder();
		par.append("parent ");
		//��Head��û��commit����par��ʼ��Ϊ40��0
		if(bufferedReader.readLine() == null) {
			for(int i = 0; i < 40; i++) {
				par.append("0");
			}
			par.append("\n");
			bufferedReader.close();
			return par.toString();
		}
		//�����ȡ�����ڶ��е�treekey
		else {
			int line = 0;
			while (bufferedReader.readLine() != null) {
				line++;
			}
			String str = "";
			for(int i = 0; i == line-1; i++) {
				str = bufferedReader.readLine();
			}
			par.append(str+"\n");
			//ɾ��readLine�е�"tree "�ַ�
			par.delete(7, 12);
			bufferedReader.close();
			return par.toString();
		}
	}

	public static String stringFromFile(File f) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
		StringBuilder str = new StringBuilder();
		str.append(bufferedReader.readLine()+"\n");
		bufferedReader.close();
		return str.toString();
	}
	
	public static String getHead() {
		return Head;
	}
}
