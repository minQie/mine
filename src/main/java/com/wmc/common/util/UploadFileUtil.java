package com.wmc.common.util;

import com.wmc.common.exception.ApiErrorCodes;
import com.wmc.common.exception.ApiException;
import com.wmc.config.AppConfig;
import com.wmc.domain.UploadFile;

/**
 * 拼接UploadFile实体参数的工具类
 *
 * @author 王敏聪
 * @date 2019/11/19 23:13
 */
public class UploadFileUtil {

    /**
     * 获取访问文件的文件名（带有拓展名）
     *
     * @param uploadFile
     * @return
     */
    public static String getFileNameWithExtension(UploadFile uploadFile) {
        return uploadFile.getSaveName() + "." + uploadFile.getExtension();
    }

    /**
     * 获取访问文件的url
     *
     * @param file
     * @return
     */
    public static String getUrl(UploadFile file) {
        projectFileSettingDetect(file);
        return file.getServerDomain() + file.getRelativePath() + file.getSaveName() + "." + file.getExtension();
    }

    /**
     * 获取访问文件绝对路径
     *
     * @param file
     * @return
     */
    public static String getDiskAbsoluteUrl(UploadFile file) {
        return file.getAbsolutePath() + file.getSaveName() + "." + file.getExtension();
    }

    /**
     * 校验文件存储时的项目文件存储配置和现在的是否匹配
     *
     * @param uploadFile
     */
    public static void projectFileSettingDetect(UploadFile uploadFile) {
        if (!uploadFile.getServerDomain().equals(AppConfig.SERVER_DOMAIN)) {
            throw new ApiException(ApiErrorCodes.CONFIG_SERVER_DOMAIN_CHANGED, "文件存储时：" + uploadFile.getServerDomain() + ", " + "现在：" + AppConfig.SERVER_DOMAIN);
        }
    }

}
