package com.douding.server.service;

import com.douding.server.domain.Chapter;
import com.douding.server.domain.ChapterExample;
import com.douding.server.dto.ChapterDto;
import com.douding.server.dto.ChapterPageDto;
import com.douding.server.dto.PageDto;
import com.douding.server.mapper.ChapterMapper;
import com.douding.server.util.CopyUtil;
import com.douding.server.util.UuidUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public void list(ChapterPageDto chapterPageDto){
        PageHelper.startPage(chapterPageDto.getPage(),chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andCourseIdEqualTo(chapterPageDto.getCourseId());
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> list = CopyUtil.copyList(chapters, ChapterDto.class);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        chapterPageDto.setList(list);
        chapterPageDto.setTotal(pageInfo.getTotal());
    }// method


    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (chapterDto.getId() == null || "".equals(chapterDto.getId())){
            String id = UUID.randomUUID().toString().substring(0, 8);
            chapter.setId(id);
            insert(chapter);
            return;
        }
        update(chapter);
    }
    //新增数据
    private void insert(Chapter chapter) {
        int i = chapterMapper.insert(chapter);
        if (i == 0){
            throw new RuntimeException();
        }
    }
    //更新数据
    private void update(Chapter chapter) {
        int i = chapterMapper.updateByPrimaryKey(chapter);
        if (i == 0){
            throw new RuntimeException();
        }
    }

    public void delete(String id) {
        int i = chapterMapper.deleteByPrimaryKey(id);
        if (i == 0){
            throw new RuntimeException();
        }
    }


    /**
     * 查询某一课程下的所有章
     */
    public List<ChapterDto> listByCourse(String courseId) {

        return null;
    }
}//end class
