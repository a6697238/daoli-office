package com.daoli.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * AUTO-GENERATED: houlu @ 2019/8/24 下午7:35
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class FileUtils {

    public static void writeFile(String fileName, String filePath, byte[] fileBytes) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File checkFile = new File(filePath + "/" + fileName);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            out.write(fileBytes);
            out.flush();
        } catch (Exception e) {
            log.error("fail to write file ", e);
        }
    }


    public static byte[] readFile(String filePath) {
        try (FileInputStream is = new FileInputStream(filePath)) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                byte[] fileData = new byte[(int) file.length()];
                is.read(fileData);
                return fileData;
            }
        } catch (Exception e) {
            log.error("fail to write file ", e);
        }
        return new byte[0];
    }
}
