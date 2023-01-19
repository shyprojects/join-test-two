package com.douding.file.controller.admin;


import com.douding.server.domain.File;
import com.douding.server.dto.FileDto;
import com.douding.server.dto.ResponseDto;

import com.douding.server.service.FileService;

import com.douding.server.util.Base64ToMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin")
public class OssController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Resource
    private FileService fileService;

    @PostMapping("/oss-append")
    public ResponseDto fileUpload(@RequestBody FileDto fileDto) throws Exception {
        fileService.save(fileDto);
        //上传文件
        MultipartFile multipartFile = Base64ToMultipartFile.base64ToMultipart(fileDto.getShard());
        java.io.File file1 = new java.io.File(FILE_PATH + fileDto.getPath());
        java.io.File file2 = new java.io.File(FILE_PATH + "/course");
        if (!file2.exists()){
            file2.mkdirs();
        }
        java.io.File file3 = new java.io.File(FILE_PATH + "/teacher");
        if (!file3.exists()){
            file3.mkdirs();
        }
        java.io.File file4 = new java.io.File(FILE_PATH + "/teachers");
        if (!file4.exists()){
            file4.mkdirs();
        }
        multipartFile.transferTo(file1);
        File file = fileService.selectByKey(fileDto.getKey());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        file.setPath(FILE_DOMAIN + file.getPath());
        responseDto.setContent(file);
        return responseDto;
    }


    @PostMapping("/oss-simple")
    public ResponseDto fileUpload(@RequestParam MultipartFile file, String use) throws Exception {
        java.io.File file1 = new java.io.File(FILE_PATH + "/course");
        if (!file1.exists()){
            file1.mkdirs();
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        return responseDto;
    }

    /*
        http://127.0.0.1:9000/file/admin/oss-check/267GleNQaeI6uaYKs22YW
        文件指纹加密值 267GleNQaeI6uaYKs22YW 验证数据库中是否已经保存过该文件
        查找文件分片位置
        无论断点传,还是全新传,都需要调用该方法进行验证
     */
    @GetMapping("/oss-check/{key}")
    public ResponseDto check(@PathVariable String key) throws Exception {
        File file = fileService.selectByKey(key);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(file);
        responseDto.setSuccess(true);
        return responseDto;
    }
}
