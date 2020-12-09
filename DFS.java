package project;

import java.io.File;
import java.util.Arrays;

public class DFS {
	
//	//��hash��MessageDigest��ת����key���ַ�����
//	public static String MDToKey(MessageDigest complete) {
//		//��hash��MessageDigest��ת��������
//        byte[] sha1 = complete.digest();
//        //������ת����key���ַ�����
//        String result = "";
//        for (byte each : sha1) {result += Integer.toString(each&0xFF, 16);}
//        return result;
//	}
//	
//	//���ַ���ת����key���ַ�����
//    public static String stringToKey(String str) throws Exception {
//    	//���ַ���ת����hash��MessageDigest��
//        MessageDigest complete = MessageDigest.getInstance("SHA-1");
//        complete.update(str.getBytes());
//      //��hash��MessageDigest��ת����key���ַ�����
//        return MDToKey(complete);
//    }
	
    //���ļ��У�file��ת����treeKey���ַ����������浽����
	public static String getTreeKey(String path) throws Exception {
		File dir = new File(path);
        File[] files = dir.listFiles();
        // ���Ϊ���򷵻ؿգ�������ļ���Ŀ¼����֤��������
        if (files==null) {
        	return null;
        	}
        else {
        	Arrays.sort(files);
        	}
        //str��¼treeValue
        StringBuilder str = new StringBuilder();
//        String str = "";
        // ���ļ���Ŀ¼����
        for (File f: files) {
            // ������ļ������ļ������ļ�key�ӵ�str
            if (f.isFile()) {
            	str.append("blob ");
                str.append("{"+GetHashSHA1.getFileHash(f)+"} ");
                str.append(f.getName()+"\n");
               //���ļ����ɵ�key��¼�������ļ�
                RecordKey.recordBlob(path + File.separator + f.getName());
                System.out.println("file "+str);
            }
            // ������ļ��У����ļ��������ļ���key���ݹ齫�ļ��У�file��ת����treeValue���ַ��������ӵ�str
            if (f.isDirectory()) {
            	str.append("tree ");
                str.append("{"+getTreeKey(path + File.separator + f.getName())+"} ");
                str.append(f.getName()+"\n");
                System.out.println("dir "+str);
            }
        }
        System.out.println("all "+str);
        //���ļ������ɵ�key��¼�������ļ�
        RecordKey.recordTree(str.toString());
        //��treeValue���ַ�����ת����treeKey���ַ�����
        return GetHashSHA1.getStringHash(str.toString());
	}
	public static void main(String[] args) throws Exception {
		getTreeKey("D:\\0.�γ�����\\java\\�½��ļ���");
	}
}
