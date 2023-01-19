package com.douding.business.controller.admin;




import com.douding.server.dto.*;
import com.douding.server.service.CourseCategoryService;
import com.douding.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    //给了日志用的
    public  static final String BUSINESS_NAME ="课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value="courseId")String courseId){

        return null;
    }

    @RequestMapping("/list")
    public ResponseDto list(CoursePageDto pageDto){
        courseService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto){
        ResponseDto responseDto = new ResponseDto();
        try {
            courseService.save(courseDto);
            responseDto.setSuccess(true);
        } catch (Exception e) {
            responseDto.setSuccess(false);
            return responseDto;
        }
        return responseDto;
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){

        ResponseDto responseDto = new ResponseDto();
        try {
            courseService.delete(id);
            responseDto.setSuccess(true);
        } catch (Exception e) {
            responseDto.setSuccess(false);
            return responseDto;
        }
        return responseDto;
    }

    //课程内容相关查找
    @GetMapping("/find-content/{id}")
    public ResponseDto findContent(@PathVariable String id) {
        CourseContentDto content = courseService.findContent(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(content);
        responseDto.setSuccess(true);
        return responseDto;
    }

    //课程内容保存
    @PostMapping("/save-content")
    public ResponseDto saveConent(@RequestBody CourseContentDto contentDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            int i = courseService.saveContent(contentDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        responseDto.setSuccess(true);
        return responseDto;
    }

    //课程排序
    @PostMapping("/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto){
        courseService.sort(sortDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        return responseDto;
    }

}//end class