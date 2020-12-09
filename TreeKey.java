package project;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TreeKey {
	
	//将hash（MessageDigest）转换成key（字符串）
	public static String MDToKey(MessageDigest complete) {
		//将hash（MessageDigest）转换成数组
        byte[] sha1 = complete.digest();
        //将数组转换成key（字符串）
        String result = "";
        for (byte each : sha1) {result += Integer.toString(each&0xFF, 16);}
        return result;
	}
	
	//将字符串转换成key（字符串）
    public static String stringToKey(String str) throws Exception {
    	//将字符串转换成hash（MessageDigest）
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(str.getBytes());
      //将hash（MessageDigest）转换成key（字符串）
        return MDToKey(complete);
    }
    
    //将文件夹（file）转换成treeKey（字符串）
	public static String getTreeKey(String path) throws Exception {
		File dir = new File(path);
        File[] files = dir.listFiles();
        // 如果为空则返回空，否则对文件夹目录排序保证按序输入
        if (files==null) {
        	return null;
        	}
        else {
        	Arrays.sort(files);
        	}
        //str记录treeValue
        String str = "";
        // 对文件夹目录遍历
        for (File f: files) {
            // 如果是文件，则将文件名和文件key加到str
            if (f.isFile()) {
                str += f.getName();
                str += getHashSHA1.getFileHash(f);
                //将文件生成的key记录到本地文件
                blobKey.recordBlob(path + File.separator + f.getName());
            }
            // 如果是文件夹，则将文件夹名和文件夹key（递归将文件夹（file）转换成treeValue（字符串））加到str
            if (f.isDirectory()) {
                str += f.getName();
                str += getTreeKey(path + File.separator + f.getName());
                //将文件夹生成的key记录到本地文件
                blobKey.recordTree(stringToKey(str), str);
            }
        }
        //将treeValue（字符串）转换成treeKey（字符串）
        return stringToKey(str);
	}
}
