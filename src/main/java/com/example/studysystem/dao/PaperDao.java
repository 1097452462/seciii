package com.example.studysystem.dao;

import com.example.studysystem.entity.Meeting;
import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PaperDao {
    List<Paper> getPapers();
    Paper getPaperById(int id);
    List<Paper> getPapersByIds(List<Integer> id);
    List<Paper> getTopPaper(List<Integer> id);
    //List<simplePaperDao> searchPapers(@Param("searchForm") SearchForm searchForm);
    List<Meeting> getMeetingTop10();
    Meeting getMeetingById(int id);
}
