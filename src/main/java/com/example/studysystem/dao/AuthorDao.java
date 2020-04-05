package com.example.studysystem.dao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.History;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.SimplePaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    List<Author> getTopAuthor_byName(List<String> name);//  同上，参数不同
    List<Author> getTopAuthor_byId(List<Integer> id);//  同上，参数不同
    List<String> getRelatedAuthor_byAuthorId(int id); // 和这个作者一起发过论文的作者，前五，根据一起发过的论文数量降序
    List<String> getRelatedAuthor_byOrgId(int id);// 和这个作者一起发过论文的机构，前五，根据一起发过的论文数量降序
    List<History> getHistory(int id);  //某个作者 按照年份发的论文数  {2015，5}，{2019，8}。。。
}
