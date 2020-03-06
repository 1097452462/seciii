package com.example.studysystem.dao;

import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PaperDao {
    List<Paper> getPapers();
    List<Paper> getPapersByIds(List<Integer> id);
    //List<simplePaperDao> searchPapers(@Param("searchForm") SearchForm searchForm);
}
