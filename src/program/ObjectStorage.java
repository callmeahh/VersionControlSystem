package program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ObjectStorage {
	// object���ļ����·��
	private static String filePath = FilepathSetting.getObjectFilepath().getAbsolutePath();

	// ��object���ļ�����objects�ļ���
	protected static void storeFromFile(String key, File file) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		String outputPath = filePath + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath));
		GZIPOutputStream gos = new GZIPOutputStream(bos);
		int count;
		byte data[] = new byte[1024];
		while ((count = bis.read(data, 0, 1024)) != -1) {
			gos.write(data, 0, count);
		}
		bis.close();
		gos.finish();
		gos.flush();
		gos.close();
//		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ���ַ������ʹ���objects�ļ���
	protected static void storeFromString(String key, String value, boolean append) throws Exception {
		String outputPath = filePath + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath, append));
		bos.write(value.getBytes());
		bos.close();
//		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// ���ַ������ʹ���ָ���ļ���
	protected static void storeFromString(String key, String value, String path, boolean append) throws Exception {
		String outputPath = path + File.separator + key;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputPath, append));
		bos.write(value.getBytes());
		bos.close();
//		System.out.println("ִ����ϣ��ļ������� " + outputPath);
	}

	// �����ļ�����
	protected static void updateFile(String content, File file, boolean append) throws Exception {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, append));
		bos.write(content.getBytes());
		bos.close();
//		System.out.println("ִ����ϣ��ļ������� " + file.getAbsolutePath());
	}

	// ��ȡobjects�ļ����������ļ�������
	private static File[] getFileList() {
		File dir = new File(filePath);
		return dir.listFiles();
	}

	// ��keyǰ��λƥ��objects�ļ������ļ������е��ļ�
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
		return n == 1 ? index : -1; // �ҵ�һ��ƥ�䷵�����±꣬���򷵻�-1
	}

	// ��keyǰ��λƥ��objects�ļ������ļ������е��ļ�����������key
	protected static String getFullName(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			String fullName = fl[flag].getName();
			return fullName;
		} else {
			System.out.println("����Ĺ�ϣֵ����ȷ�����������룡");
			return null;
		}
	}

	// ��keyǰ��λƥ��objects�ļ������ļ������е��ļ�������value
	protected static String searchValue(String hash) throws Exception {
		File[] fl = getFileList();
		int flag = findSubstring(fl, hash);
		if (flag != -1) {
			String value = getContent(fl[flag]);
//			System.out.println(new String(getContent(fl[flag]).getBytes(), "UTF-8"));
			return value;
		} else {
			System.out.println("����Ĺ�ϣֵ����ȷ�����������룡");
			return null;
		}
	}

	// ��ȡ�ļ��е�����
	protected static String getContent(File file) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String str = "";
		StringBuilder sb = new StringBuilder();
		sb.append(str = bufferedReader.readLine());
		while ((str = bufferedReader.readLine()) != null) {
			sb.append("\n" + str);
		}
		bufferedReader.close();
		return sb.toString();
	}

	// ɾ��ָ���ļ�������������
	protected static boolean deleteDir(String path) {
		File file = new File(path);
		if (!file.exists()) {// �ж��Ƿ��ɾ��Ŀ¼�Ƿ����
			return false;
		}
		String[] content = file.list();// ȡ�õ�ǰĿ¼�������ļ����ļ���
		for (String name : content) {
			if (name.equals(".git")) {
				continue;
			}
			File temp = new File(path, name);
			if (temp.isDirectory()) {// �ж��Ƿ���Ŀ¼
				deleteDir(temp.getAbsolutePath());// �ݹ���ã�ɾ��Ŀ¼�������
				temp.delete();// ɾ����Ŀ¼
			} else {
				if (!temp.delete()) {// ֱ��ɾ���ļ�
					System.err.println("Failed to delete " + name);
				}
			}
		}
		return true;
	}

	// ��blob���ͻ�ԭΪ�ļ�
	protected static void decompress(String fileName, String key, String path) throws Exception {
		File f = new File(FilepathSetting.getObjectFilepath().getAbsolutePath() + File.separator + key);
		InputStream is = new FileInputStream(f);
		String newFile = path + File.separator + fileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
		GZIPInputStream gis = new GZIPInputStream(is);
		int count;
		byte data[] = new byte[1024];
		while ((count = gis.read(data, 0, 1024)) != -1) {
			bos.write(data, 0, count);
		}
		gis.close();
		bos.close();
	}

	// ��value��ʽ���ɶ�ά����
	protected static String[][] formatValue(String key) {
		String value = null;
		String[][] formattedValue;
		try {
			value = ObjectStorage.searchValue(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] first = value.split("\n");
		formattedValue = new String[first.length][];
		for (int i = 0; i < first.length; i++) {
			String[] second = { first[i].substring(0, 4), first[i].substring(5, 45), first[i].substring(46) };
			formattedValue[i] = new String[second.length];
			for (int j = 0; j < second.length; j++) {
				formattedValue[i][j] = second[j];
			}
		}
		return formattedValue;
	}

	// ����λ�����еļ�¼��ֵ��ԭ���ļ�
	protected static void restoreFiles(String[][] value_2d, String path) {
		for (int i = 0; i < value_2d.length; i++) {
			if (value_2d[i][0].equals("blob")) {
				try {
					decompress(value_2d[i][2], value_2d[i][1], path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (value_2d[i][0].equals("tree")) {
				new File(path + File.separator + value_2d[i][2]).mkdirs();
				restoreFiles(formatValue(value_2d[i][1]), path + File.separator + value_2d[i][2]);
			}
		}
	}

}
