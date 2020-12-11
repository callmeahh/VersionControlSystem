package project;

import java.io.File;
import java.util.Arrays;

public class DFS {
	
//	//将hash（MessageDigest）转换成key（字符串）
//	public static String MDToKey(MessageDigest complete) {
//		//将hash（MessageDigest）转换成数组
//        byte[] sha1 = complete.digest();
//        //将数组转换成key（字符串）
//        String result = "";
//        for (byte each : sha1) {result += Integer.toString(each&0xFF, 16);}
//        return result;
//	}
//	
//	//将字符串转换成key（字符串）
//    public static String stringToKey(String str) throws Exception {
//    	//将字符串转换成hash（MessageDigest）
//        MessageDigest complete = MessageDigest.getInstance("SHA-1");
//        complete.update(str.getBytes());
//      //将hash（MessageDigest）转换成key（字符串）
//        return MDToKey(complete);
//    }
	
    //将文件夹（file）转换成treeKey（字符串）并保存到本地
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
        StringBuilder str = new StringBuilder();
//        String str = "";
        // 对文件夹目录遍历
        for (File f: files) {
            // 如果是文件，则将文件名和文件key加到str
            if (f.isFile()) {
            	str.append("blob ");
                str.append("{"+GetHashSHA1.getFileHash(f)+"} ");
                str.append(f.getName()+"\n");
               //将文件生成的key记录到本地文件
                RecordKey.recordBlob(path + File.separator + f.getName());
                System.out.println("file "+str);
            }
            // 如果是文件夹，则将文件夹名和文件夹key（递归将文件夹（file）转换成treeValue（字符串））加到str
            if (f.isDirectory()) {
            	str.append("tree ");
                str.append("{"+getTreeKey(path + File.separator + f.getName())+"} ");
                str.append(f.getName()+"\n");
                System.out.println("dir "+str);
            }
        }
        System.out.println("all "+str);
        //将文件夹生成的key记录到本地文件
        RecordKey.recordTree(str.toString());
        //将treeValue（字符串）转换成treeKey（字符串）
        return GetHashSHA1.getStringHash(str.toString());
	}
	public static void main(String[] args) throws Exception {
		getTreeKey("D:\\0.课程资料\\java\\新建文件夹");
	}
}
