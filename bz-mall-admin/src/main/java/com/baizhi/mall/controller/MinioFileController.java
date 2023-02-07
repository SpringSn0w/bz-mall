package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.vo.Result;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@RestController
@CrossOrigin
public class MinioFileController {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endpoint;

    @PostMapping("/minio/upload")
    public Result upload(MultipartFile file){
        /*文件名*/
        String filename = file.getOriginalFilename();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(date);
        String newName = format+"/"+UUID.randomUUID().toString() + filename;
        try {
            //文件上传
            InputStream in = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(newName)
                    .stream(in, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            in.close();
        } catch (Exception e) {
            return Result.error();
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("url",endpoint + "/" + bucketName + "/" + newName);
        map.put("name",newName);
        return Result.ok(map);
    }

    @PostMapping("/minio/delete")
    public Result deleteMinio(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(date);
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(format+"/"+objectName).build());
        return Result.ok();
    }
}
