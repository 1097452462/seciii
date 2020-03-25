package com.example.studysystem.service.org;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface OrgService {
     Response getSimplePaperByOrg(String name);
     Response getOrgs();
     Response searchOrgs(String name,String num);
}
