package com.example.studysystem.dao;

import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.SimplePaper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SimplePaperDao {
    List<SimplePaper> getSimplePaperByOrg(String name);
    List<SimplePaper> getSimplePapers();
    List<Paper> getSimplePapersByIds(List<Integer> id);
    List<SimplePaper> getSimplePaperByAuthor(String name);
}
