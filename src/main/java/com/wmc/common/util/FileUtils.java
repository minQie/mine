package com.wmc.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 文件工具类
 *
 * @author 王敏聪
 * @date 2019年11月29日13:47:19
 */
public class FileUtils {

    /**
     * 文件上传：获取文件名（不包括“.拓展名”）
     *
     * @param file
     * @return
     */
    public static String getFileName(MultipartFile file) {
        int lastPointIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        return file.getOriginalFilename().substring(0, lastPointIndex);
    }

    /**
     * 文件上传：获取文件拓展名（后缀名）
     *
     * @param file
     * @return
     */
    public static String getExtension(MultipartFile file) {
        int lastPointIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        return file.getOriginalFilename().substring(lastPointIndex + 1);
    }

}