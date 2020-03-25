package com.example.studysystem.service.author;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService  {
    Response getSimplePaperByAuthor(String name);
    Response getAuthors();
    Response searchAuthors(String name,String num);
}
