package com.example.studysystem.dao;

import com.example.studysystem.entity.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrgDao {
    List<Org> getOrgs();
    List<Org> searchOrgs(@Param("name") String name, @Param("num") int num);
    String  getPaperIdByOrg(@Param("name") String name);
}
