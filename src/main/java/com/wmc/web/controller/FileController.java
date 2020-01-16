package com.wmc.web.controller;

import com.wmc.common.exception.ApiErrorCodes;
import com.wmc.common.exception.ApiException;
import com.wmc.common.util.UploadFileUtils;
import com.wmc.config.AppConfig;
import com.wmc.domain.UploadFile;
import com.wmc.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传、下载
 *
 * @author 王敏聪
 * @date 2019/11/14 14:51
 */
@Api(tags = "文件模块", description = "文件的上传和下载")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileController {

    private final FileService fileService;

    /**
     * 上传文件（上传文件的键“file”）
     *
     * @return 访问上传文件的url
     */
    @ApiOperation("文件上传")
    @ApiImplicitParam(name = "file", value = "要上传的文件", required = true)
    @PostMapping("/fileUpload")
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传的文件为空");
        }
        try {
            return fileService.save(file);
        } catch (IOException e) {
            throw new ApiException(ApiErrorCodes.FILE_UPLOAD_FAIL, e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param fileName  文件实际存储名
     * @param extension 文件后缀名
     * @param response  Spring注入
     */
    @ApiOperation("文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名", required = true),
            @ApiImplicitParam(name = "extension", value = "拓展名", required = true)
    })
    @GetMapping(AppConfig.RELATIVE_PATH + "{fileName}.{extension}")
    public void requestImage(@PathVariable("fileName") String fileName, @PathVariable("extension") String extension, @ApiIgnore HttpServletResponse response) throws IOException {
        // 1、文件信息查询
        UploadFile uploadFile = fileService.findByFileNameAndExtension(fileName, extension);
        File file = new File(UploadFileUtils.getDiskAbsoluteUrl(uploadFile));
        // 2、文件存在校验
        if (!file.exists()) {
            throw new ApiException(ApiErrorCodes.FILE_ALREADY_DELETE);
        }
        // 3、设置响应头，将文件写入输出流
        try {
            // 响应类型：.*（ 二进制流，不知道下载文件类型）
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 浏览器弹出下载框
            response.setHeader("Content-Disposition", "attachment;filename=" + "\"" + UploadFileUtils.getFileNameWithExtension(uploadFile) + "\"");
            // 文件大小
            response.setContentLengthLong(FileUtils.sizeOf(file));
            response.getOutputStream().write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            // 要是下载的是图片的话，可以像下面这样给一个项目中内置的默认图片
            //            File defaultImage = ResourceUtils.getFile("classpath:static/imgs/noimage.png");
            //
            //            // 设置ContentType
            //            response.setContentType("image/*");
            //            response.getOutputStream().write(FileUtils.toByteArray(defaultImage.getPath()));
        }
    }

}
