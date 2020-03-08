package com.example.studysystem.service.author;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService  {
    public Response getSimplePaperByOrg(String name);
    Response orderAuthors();
    Response searchAuthors(String name,String num);
}
