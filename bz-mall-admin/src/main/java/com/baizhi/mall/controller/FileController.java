package com.baizhi.mall.controller;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endpoint;

    //文件上传的方法,上传成功后需要把文件上传到哪交给前端
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPic(MultipartFile brandPic) throws IOException {
        String originalFilename = brandPic.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + originalFilename;
        try {
            //文件上传
            InputStream in = brandPic.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(newName)
                    .stream(in, brandPic.getSize(), -1)
                    .contentType(brandPic.getContentType())
                    .build());
            in.close();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("上传失败");
        }
        return ResponseEntity.ok(endpoint + "/" + bucketName + "/" + newName);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable("fileName") String fileName,
                         HttpServletResponse response) throws IOException {
        InputStream in = null;
        try {
            // 获取对象信息
            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.
                    builder().
                    bucket(bucketName).
                    object(fileName).
                    build());
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //文件下载
            in = minioClient.getObject(
                    GetObjectArgs.
                            builder().
                            bucket(bucketName).
                            object(fileName).
                            build());
            FileCopyUtils.copy(in, response.getOutputStream());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
