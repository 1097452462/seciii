package com.example.studysystem.dao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AuthorDao {
    List<Author> getAuthors();
    Author getAuthorById(@Param("id")int id);
    List<Author> searchAuthors(@Param("name") String name,@Param("num") int num);
    String  getPaperIdByAuthor(@Param("id") int id);
}
