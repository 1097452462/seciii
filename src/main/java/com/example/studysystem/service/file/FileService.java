package com.example.studysystem.service.file;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    Response getFiles();
    Response addFile(MultipartFile file);
    Response updateDB();
}
