package com.douding.server.service;

import com.douding.server.domain.Category;
import com.douding.server.domain.CategoryExample;
import com.douding.server.domain.Teacher;
import com.douding.server.domain.TeacherExample;
import com.douding.server.dto.CategoryDto;
import com.douding.server.dto.TeacherDto;
import com.douding.server.dto.PageDto;
import com.douding.server.mapper.TeacherMapper;
import com.douding.server.util.CopyUtil;
import com.douding.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;


    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        pageDto.setList(teacherDtoList);
    }

    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if(teacherDto.getId() == null || "".equals(teacherDto.getId())){
            insert(teacher);
            return;
        }
        update(teacher);
    }

    //新增数据
    private void insert(Teacher teacher) {
        teacher.setId(UUID.randomUUID().toString().substring(0,8));
        int i = teacherMapper.insert(teacher);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    //更新数据
    private void update(Teacher teacher) {
        int i = teacherMapper.updateByPrimaryKey(teacher);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    public void delete(String id) {
        int i = teacherMapper.deleteByPrimaryKey(id);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    public List<TeacherDto> all() {
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        return teacherDtoList;
    }


    /**
     * 查找
     * @param id
     */
    public TeacherDto findById(String id) {

        return null;
    }
}//end class
