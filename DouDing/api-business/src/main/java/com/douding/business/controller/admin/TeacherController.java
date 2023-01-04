package com.douding.business.controller.admin;




import com.douding.server.dto.TeacherDto;
import com.douding.server.dto.PageDto;
import com.douding.server.dto.ResponseDto;
import com.douding.server.service.TeacherService;
import com.douding.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);
    //给了日志用的
    public  static final String BUSINESS_NAME ="讲师";

    @Resource
    private TeacherService teacherService;

    @RequestMapping("/list")
    public ResponseDto list(PageDto pageDto){
        teacherService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto){
        ResponseDto responseDto = new ResponseDto();
        try {
            teacherService.save(teacherDto);
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
            teacherService.delete(id);
            responseDto.setSuccess(true);
        } catch (Exception e) {
            responseDto.setSuccess(false);
            return responseDto;
        }
        return responseDto;
    }

    @RequestMapping("/all")
    public ResponseDto all(){
        List<TeacherDto> list = teacherService.all();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        responseDto.setContent(list);
        return responseDto;
    }

}//end class