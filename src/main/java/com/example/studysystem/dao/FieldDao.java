package com.example.studysystem.dao;

import com.example.studysystem.entity.Field;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FieldDao {
    List<Field> getFields();
    String getPaper_list(int id);
}
