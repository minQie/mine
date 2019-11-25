package com.wmc.domain;

import com.wmc.domain.base.BaseWhenDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 文件上传实体
 *
 * @author 王敏聪
 * @date 2019/11/18 10:16
 */
@Builder
@Setter
@Getter
@Entity
public class UploadFile extends BaseWhenDomain {

    /** 项目域名 */
    @Column(length = 63, nullable = false)
    String serverDomain;

    /** 文件上传目录 */
    @Column(length = 127, nullable = false)
    String absolutePath;

    /** 从服务器域名开始的，后面的代理上传文件夹的相对路径（在本项目中就是“下载文件接口”的uri的前段） */
    @Column(length = 63, nullable = false)
    String relativePath;

    /** 文件上传 初始文件名（不含拓展名） */
    @Column(nullable = false)
    String originalName;

    /** 文件上传 实际保存在磁盘上的文件名（不包含拓展名） */
    @Column(length = 63, nullable = false)
    String saveName;

    /** 拓展名 */
    @Column(length = 15, nullable = false)
    String extension;

    /** 文件大小（单位字节） */
    @Column(length = 64, nullable = false)
    Long size;

}
