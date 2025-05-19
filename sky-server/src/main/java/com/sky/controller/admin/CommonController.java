package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    private static final String BASE_PATH = "F:/JavaWeb/uploads/";

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        // 上传oss版本
        try {
            // 创建目录（如果不存在）
            File dir = new File(BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 使用 UUID 防止重复
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // 保存文件到本地路径
            File dest = new File(BASE_PATH + fileName);
            file.transferTo(dest);

            // 返回文件保存的本地路径或下载 URL（可以根据需求换成相对路径）
            System.out.println("/uploads/" + fileName);
            return Result.success("/uploads/" + fileName);

        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
//        try {
//            // 原始文件名
//            String originalFilename = file.getOriginalFilename();
//            // 截取原始文件名的后缀
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            // 构造新文件名称
//            String objectName = UUID.randomUUID().toString() + extension;
//
//            // 文件的请求路径
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//            return Result.success(filePath);
//        } catch (IOException e) {
//            log.error("文件上传失败：{}", e);
//        }
//        return null;
    }
}
