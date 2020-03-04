package com.example.studysystem.service;

import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SearchForm;
import org.springframework.stereotype.Service;

@Service
public interface PaperService {
    Response plugPapers();
    Response getPapers();
    Response searchPapers(SearchForm searchForm);
}
