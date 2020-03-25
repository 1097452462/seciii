package com.example.studysystem.service.org;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Org;
import com.example.studysystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService{
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private SimplePaperDao simplePaperDao;


    @Override
    public Response getSimplePaperByOrg(String name) {
        try{
            String[] temp=orgDao.getPaperIdByOrg(name).split(";");
            List<Integer> paperId=new ArrayList<>();
            for(String d:temp)paperId.add(Integer.parseInt(d));
            return Response.buildSuccess(simplePaperDao.getSimplePapersByIds(paperId));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getOrgs() {
        try{
            return Response.buildSuccess(orgDao.getOrgs());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchOrgs(String name, String num) {
        try {
            int d=0;
            if(!num.isEmpty())d=Integer.parseInt(num);
            List<Org> orgs= orgDao.searchOrgs(name,d);
            return Response.buildSuccess(orgs);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
