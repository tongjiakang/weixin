package com.vanke.mhj.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemorySql {
    public Map<String, String> sqlMap = new HashMap<String, String>();
    public static final String dir = MemorySql.class.getResource("").getPath();
    public List<File> filelist = new ArrayList<File>();

    public void init() {
        String classPath = dir.substring(1, dir.indexOf("classes") + 8);
        try {
        	classPath= URLDecoder.decode(classPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        getFileList(classPath);
        for (File file : filelist) {
            readFile(file.getAbsolutePath());
        }
    }

    private void getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith(".sql")) { // 判断文件名是否以.sql结尾
                    String strFileName = files[i].getAbsolutePath();
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
    }

    private void readFile(String path) {
        StringBuffer sqlSb = new StringBuffer();
        try {
            InputStream sqlFileIn = new FileInputStream(path);

            byte[] buff = new byte[sqlFileIn.available()];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead));
            }
        } catch (Exception e) {
            System.out.println("文件读取失败");
        }
        String mapName = path.substring(path.lastIndexOf("classes") + 8);
        String[] maps = mapName.split("\\\\");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maps.length; i++) {
            sb.append(maps[i]);
        }
        sqlMap.put(sb.toString(), sqlSb.toString());
    }
}
