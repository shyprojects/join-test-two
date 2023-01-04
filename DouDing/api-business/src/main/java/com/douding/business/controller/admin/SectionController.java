package com.douding.business.controller.admin;




import com.douding.server.dto.SectionDto;
import com.douding.server.dto.PageDto;
import com.douding.server.dto.ResponseDto;
import com.douding.server.dto.SectionPageDto;
import com.douding.server.service.SectionService;
import com.douding.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    //给了日志用的
    public  static final String BUSINESS_NAME ="小节";

    @Resource
    private SectionService sectionService;

    @RequestMapping("/list")
    public ResponseDto list(SectionPageDto pageSectionDto){
        sectionService.list(pageSectionDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        responseDto.setContent(pageSectionDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto){
        ResponseDto responseDto = new ResponseDto();
        try {
            sectionService.save(sectionDto);
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
            sectionService.delete(id);
            responseDto.setSuccess(true);
        } catch (Exception e) {
            responseDto.setSuccess(false);
            return responseDto;
        }
        return responseDto;
    }

}//end class