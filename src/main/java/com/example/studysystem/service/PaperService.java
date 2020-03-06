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
    Response getSimplePapers();
    void handle(List<SimplePaper> origin);
    Response getPaperById(int id);

    Response searchPapers(SimplePaper simplePaper);
}
