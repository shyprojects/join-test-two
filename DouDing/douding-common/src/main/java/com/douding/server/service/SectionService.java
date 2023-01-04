package com.douding.server.service;

import com.douding.server.domain.ChapterExample;
import com.douding.server.domain.Section;
import com.douding.server.domain.SectionExample;
import com.douding.server.domain.Teacher;
import com.douding.server.dto.SectionDto;
import com.douding.server.dto.PageDto;
import com.douding.server.dto.SectionPageDto;
import com.douding.server.enums.SectionChargeEnum;
import com.douding.server.mapper.SectionMapper;
import com.douding.server.util.CopyUtil;
import com.douding.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.List;


import java.util.Date;
import java.util.UUID;


@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseService courseService;


    /**
     * 列表查询
     */
    public void list(SectionPageDto sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(),sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        sectionExample.createCriteria().andCourseIdEqualTo(sectionPageDto.getCourseId()).andChapterIdEqualTo(sectionPageDto.getChapterId());
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        List<SectionDto> sectionDtos = CopyUtil.copyList(sections, SectionDto.class);
        sectionPageDto.setList(sectionDtos);
        sectionPageDto.setTotal(pageInfo.getTotal());
    }


    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if(sectionDto.getId() == null || "".equals(sectionDto.getId())){
            String id = UUID.randomUUID().toString().substring(0, 8);
            section.setId(id);
            insert(section);
            return;
        }
        update(section);
    }

    //新增数据
    private void insert(Section section) {
        int i = sectionMapper.insert(section);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    //更新数据
    private void update(Section section) {
        int i = sectionMapper.updateByPrimaryKey(section);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    public void delete(String id) {
        int i = sectionMapper.deleteByPrimaryKey(id);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    /**
     * 查询某一课程下的所有节
     */
    public List<SectionDto> listByCourse(String courseId) {

        return null;
    }

}//end class
