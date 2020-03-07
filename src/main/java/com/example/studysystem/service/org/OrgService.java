package com.example.studysystem.service.org;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface OrgService {

     Response orderOrganizations();
     Response searchOrg(String name,String num);
}
