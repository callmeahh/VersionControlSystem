package project;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TreeKey {
	
	//��hash��MessageDigest��ת����key���ַ�����
	public static String MDToKey(MessageDigest complete) {
		//��hash��MessageDigest��ת��������
        byte[] sha1 = complete.digest();
        //������ת����key���ַ�����
        String result = "";
        for (byte each : sha1) {result += Integer.toString(each&0xFF, 16);}
        return result;
	}
	
	//���ַ���ת����key���ַ�����
    public static String stringToKey(String str) throws Exception {
    	//���ַ���ת����hash��MessageDigest��
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(str.getBytes());
      //��hash��MessageDigest��ת����key���ַ�����
        return MDToKey(complete);
    }
    
    //���ļ��У�file��ת����treeKey���ַ�����
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
        String str = "";
        // ���ļ���Ŀ¼����
        for (File f: files) {
            // ������ļ������ļ������ļ�key�ӵ�str
            if (f.isFile()) {
                str += f.getName();
                str += getHashSHA1.getFileHash(f);
                //���ļ����ɵ�key��¼�������ļ�
                blobKey.recordBlob(path + File.separator + f.getName());
            }
            // ������ļ��У����ļ��������ļ���key���ݹ齫�ļ��У�file��ת����treeValue���ַ��������ӵ�str
            if (f.isDirectory()) {
                str += f.getName();
                str += getTreeKey(path + File.separator + f.getName());
                //���ļ������ɵ�key��¼�������ļ�
                blobKey.recordTree(stringToKey(str), str);
            }
        }
        //��treeValue���ַ�����ת����treeKey���ַ�����
        return stringToKey(str);
	}
}
