package com.example.studysystem.service.author;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService  {
    Response getAuthors();
    Response getAuthorById(int id);
    Response getSimplePaperByAuthor(int id);
    Response searchAuthors(String name,String num);
}
