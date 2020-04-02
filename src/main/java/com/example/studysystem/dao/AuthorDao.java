package com.example.studysystem.dao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.SimplePaper;
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
    Integer getPaperNum(@Param("id") int id);       //文章总数
    Integer getCitationSum(@Param("id") int id);    //某个作者文章被引用数总和
    List<Integer> getTopPaperIds(@Param("id") int id);   //某个作者所有文章引用数最多的前五
    List<String> getKeywords(@Param("id") int id);
    List<Author> getTopAuthor_paperNum();    // 论文数前10 作者
    List<Author> getTopAuthor_citationSum();   //引用数前10 作者
    List<Author> getTopAuthor_point();   // 综合排名前10  70%论文数 30%引用数   作者
}
