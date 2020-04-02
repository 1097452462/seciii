package com.example.studysystem.service.field;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface FieldService {
    Response getFields();
    Response getTopAuthors(int id);
    Response getTopOrgs(int id);
    Response getTopPapers(int id);
    Response getPaperNum(int id);
    Response getCitationSum(int id);
    Response getTop10Field(int mode);
}
