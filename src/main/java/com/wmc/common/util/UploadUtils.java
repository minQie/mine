package com.wmc.common.util;

import com.wmc.config.AppConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author 王敏聪
 * @Date 2019/11/15 11:34
 */
public class UploadUtils {

    /**
     * 序列化文件到指定目录，并返回文件名
     *
     * @param file 要保存的文件
     * @return 实际保存的文件名
     * @throws IOException
     */
    public static String upload(MultipartFile file, File uploadDirectory) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String extension = FileUtils.getExtension(file);

        String fileNameWithExtension = fileName + "." + extension;

        org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadDirectory, fileNameWithExtension));
        return fileName;
    }

    /**
     * @param relativeDirectoryPath
     * @return
     * @throws IOException
     */
    public static File getUploadDirectory(String relativeDirectoryPath) throws IOException {
        File fileUploadDirectory = new File(AppConfig.ABSOLUTE_PATH + relativeDirectoryPath);

        /*
         * 前提知识：windows下不允许同一目录下存在同名文件（如果存在“abc.txt”，你是创建不了名为“abc”的文件夹的）
         * 源码逻辑如下：
         * 如果同名文件存在：是一个目录 -> true
         * 如果同名文件不存在：创建目录成功 | (创建目录失败 & 是一个目录) -> true
         */
        org.apache.commons.io.FileUtils.forceMkdir(fileUploadDirectory);
        return fileUploadDirectory;
    }

}
