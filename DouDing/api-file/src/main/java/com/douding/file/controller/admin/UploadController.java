package com.douding.file.controller.admin;


import com.douding.server.dto.CourseContentFileDto;
import com.douding.server.dto.FileDto;
import com.douding.server.dto.ResponseDto;
import com.douding.server.service.CourseContentFileService;
import com.douding.server.service.FileService;
import com.douding.server.service.TestService;
import com.douding.server.util.Base64ToMultipartFile;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/*
    返回json 应用@RestController
    返回页面  用用@Controller
 */
@RequestMapping("/admin/file")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    public  static final String BUSINESS_NAME ="文件上传";
    @Resource
    private TestService testService;

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Autowired
    private CourseContentFileService courseContentFileService;
    @Resource
    private FileService fileService;
    @RequestMapping("/content/upload")
    public ResponseDto upload(@RequestParam MultipartFile file,String use) throws Exception {
        java.io.File file1 = new java.io.File(FILE_PATH + "/course");
        if (!file1.exists()){
            file1.mkdirs();
        }
        file.transferTo(new File(FILE_PATH + "/course/" + file.getOriginalFilename()));
        CourseContentFileDto fileDto = new CourseContentFileDto();
        fileDto.setId();
        courseContentFileService.save(fileDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        return responseDto;
    }

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        fileService.save(fileDto);
        System.err.println(fileDto);
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
        com.douding.server.domain.File resFile = fileService.selectByKey(fileDto.getKey());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        resFile.setPath(FILE_DOMAIN + resFile.getPath());
        responseDto.setContent(resFile);
        return responseDto;
    }
    //合并分片
    public void merge(FileDto fileDto) throws Exception {
        LOG.info("合并分片开始");
    }
    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) throws Exception {
        LOG.info("检查上传分片开始：{}", key);
        com.douding.server.domain.File file = fileService.selectByKey(key);
        ResponseDto responseDto = new ResponseDto();
        if (file != null){
            responseDto.setContent(file);
        }
        responseDto.setSuccess(true);
        return responseDto;
    }
}//end class
