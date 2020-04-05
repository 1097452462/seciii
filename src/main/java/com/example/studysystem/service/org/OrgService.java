package com.example.studysystem.service.org;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface OrgService {
     Response getOrgs();
     Response getOrgById(int id);
     Response getSimplePaperByOrg(int id);
     Response searchOrgs(String name,String num);
     Response getAuthorNum(int id);

     Response getPaperNum(int id);
     Response getCitationSum(int id);
     Response getTopPaper(int id);
     Response getTopKeyword(int id);

     Response getTopAuthor(int id);
     Response getTop10Org(int mode);
}
