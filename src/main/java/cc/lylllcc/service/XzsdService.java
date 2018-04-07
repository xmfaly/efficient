package cc.lylllcc.service;

import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import cc.lylllcc.szsdmodel.JwxtCourse;
import cc.lylllcc.szsdmodel.JwxtScore;
import cc.lylllcc.util.SzsdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XzsdService {

    @Autowired
    private UserRepository userRepository;


    public Object getCourseInfo(String username) throws Exception {
        User user = userRepository.findFirstByUsername(username);
        if(user == null){
            return new JsonMes(1,"用户不存在");
        }
        String stuid = user.getStudentId();
        String stdpwd = user.getIpassword();

        if(stuid == null || stdpwd == null){
            return new JsonMes(2,"没有绑定数字石大");
        }
        try {


            String szsdCookie = SzsdUtils.getSzsdCookie(stuid,stdpwd);
            String jwxtCookie = SzsdUtils.getJwxtCookie(szsdCookie);
            List<JwxtCourse> courseInfo =  SzsdUtils.getCourseInfo("","",jwxtCookie);


            
            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("message","操作成功");
            map.put("course",courseInfo);
            return map;
        }catch (Exception e){
            return new JsonMes(3,"数字石大账号或密码错误");
        }

    }

    public Object getScore(String username) throws Exception {
        User user = userRepository.findFirstByUsername(username);
        if(user == null){
            return new JsonMes(1,"用户不存在");
        }
        String stuid = user.getStudentId();
        String stdpwd = user.getIpassword();

        if(stuid == null || stdpwd == null){
            return new JsonMes(2,"没有绑定数字石大");
        }
        try {


            String szsdCookie = SzsdUtils.getSzsdCookie(stuid,stdpwd);
            String jwxtCookie = SzsdUtils.getJwxtCookie(szsdCookie);
            List<JwxtScore> scores =  SzsdUtils.getScore("",jwxtCookie);

            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("message","操作成功");
            map.put("score",scores);
            return map;
        }catch (Exception e){
            return new JsonMes(3,"数字石大账号或密码错误");
        }

    }


}
