package com.example.studysystem.service;

import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SearchForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PaperService {
    Response addFile(MultipartFile file);
    Response plugPapers();
    Response getPapers();
    Response searchPapers(SearchForm searchForm);
}
