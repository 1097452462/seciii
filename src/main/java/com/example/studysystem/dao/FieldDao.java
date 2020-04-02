package com.example.studysystem.dao;

import com.example.studysystem.entity.Field;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FieldDao {
    List<Field> getFields();
    Integer getPaperNum(@Param("id") int id);       //文章总数
    Integer getCitationSum(@Param("id") int id);    //某个机构文章被引用数总和
    List<Integer> getTopPaperIds(@Param("id") int id);   //某个机构所有文章引用数最多的前五
    List<Field> getTopField_paperNum();    // 论文数前10 机构
    List<Field> getTopField_citationSum();   //引用数前10 机构
    List<Field> getTopField_point();   // 综合排名前10  70%论文数 30%引用数   机构
}
