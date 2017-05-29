package com.army.autoinstallapp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/29
 * @description
 */
public class FileUtils {

    public static void appendToFile(String content, String filePath) {

        File f = new File(filePath);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        RandomAccessFile file;
        try {
            file = new RandomAccessFile(f, "rw");
            long seekPosition = file.length();
            file.seek(seekPosition);
            int percount = 1000;
            int count = (int) Math.ceil(content.length() * 1.0 / percount);
            for (int i = 0; i < count; i++) {
                if ((i + 1) * percount < content.length()) {
                    file.write(content.substring(i * percount, (i + 1) * percount).getBytes("utf-8"));
                    file.seek(seekPosition + (i + 1) * percount);
                } else {
                    file.write(content.substring(i * percount, content.length()).getBytes("utf-8"));
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
