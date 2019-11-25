package com.wmc.service;

import com.wmc.common.exception.ApiErrorCodes;
import com.wmc.common.exception.ApiException;
import com.wmc.config.AppConfig;
import com.wmc.domain.UploadFile;
import com.wmc.domain.query.QUploadFile;
import com.wmc.common.util.DateUtil;
import com.wmc.common.util.FileUtil;
import com.wmc.common.util.UploadFileUtil;
import com.wmc.common.util.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 王敏聪
 * @date 2019/11/17 21:04
 */
@Service
public class FileService {

    /**
     * 保存上传文件
     *
     * @param file
     * @return 保存文件的真实存储文件名
     * @throws IOException
     */
    public String save(MultipartFile file) throws IOException {
        // 1、准备保存文件的文件夹
        String relativeDirectoryPath = DateUtil.getTodayYearMonthDayDateString() + "/";
        File uploadDirectory = UploadUtil.getUploadDirectory(relativeDirectoryPath);

        // 2、保存文件
        String fileSaveName = UploadUtil.upload(file, uploadDirectory);

        // 3、保存文件相关信息
        UploadFile uploadFile = UploadFile.builder()
                .serverDomain(AppConfig.SERVER_DOMAIN)
                .absolutePath(AppConfig.ABSOLUTE_PATH + relativeDirectoryPath)
                .relativePath(AppConfig.RELATIVE_PATH)
                .originalName(FileUtil.getFileName(file))
                .saveName(fileSaveName)
                .extension(FileUtil.getExtension(file))
                .size(file.getSize())
                .build();
        uploadFile.save();

        // 4、返回访问文件的url
        return UploadFileUtil.getUrl(uploadFile);
    }

    /**
     * 根据实际文件的存储名查找文件信息
     *
     * @param fileName
     * @param extension
     * @return
     */
    public UploadFile findByFileNameAndExtension(String fileName, String extension) {
        UploadFile uploadFile = new QUploadFile()
                .extension.equalTo(extension)
                .saveName.equalTo(fileName)
                .findOne();
        if (uploadFile == null) {
            throw new ApiException(ApiErrorCodes.FILE_NOT_EXIST, "不存在名为 " + fileName + " 的文件!");
        }
        return uploadFile;
    }

}