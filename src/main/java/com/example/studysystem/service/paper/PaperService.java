package com.example.studysystem.service.paper;

import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PaperService {
    Response getSimplePapers();
    Response getPapers();
    Response getPapersById(int id);
    Response searchPapers(SimplePaper simplePaper);
}
