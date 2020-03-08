package com.example.studysystem.service.org;
import com.example.studysystem.csv.ReadCSV;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Paper;
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
            return Response.buildSuccess(simplePaperDao.getSimplePaperByOrg(name));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response orderOrganizations() {
        try{
            //System.out.println(orgDao.sortByAffiliation().size());
            return Response.buildSuccess(orgDao.sortByAffiliation());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchOrg(String name, String num) {
        try{
            List<List<String>> orgs=orgDao.sortByAffiliation();
            List<List<String>> ans=new ArrayList<>();
            for(List<String> t:orgs){
                boolean flag1=true,flag2=true;
                if(!name.isEmpty()) {
                    //System.out.println(t.get(0).toLowerCase() +"   "+name.toLowerCase());
                    /*if (!t.get(0).toLowerCase().contains(name.toLowerCase())) {
                        flag1 = false;
                    }*/
                    if (!name.isEmpty()) {
                        String temp = name.toLowerCase();
                        temp.replaceAll(";", " ");
                        String list[] = temp.split(" ");
                        for (String x : list) {
                            if (!t.get(0).toLowerCase().contains(x)){
                                flag1=false;
                                break;
                            }
                        }
                    }
                }
                if(!flag1)continue;
                if(!num.isEmpty()) {
                    if (Integer.parseInt(t.get(1)) < Integer.parseInt(num)) flag2 = false;
                }
                if(flag2)ans.add(t);

            }
            return Response.buildSuccess(ans);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
