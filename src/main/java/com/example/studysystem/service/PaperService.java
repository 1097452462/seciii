package com.example.studysystem.service;

import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PaperService {
    Response addFile(MultipartFile file);
    Response plugPapers();

    Response getPapers();
    Response orderAuthors();
    Response orderOrganizations();

    Response searchPapers(SimplePaper simplePaper);
}
