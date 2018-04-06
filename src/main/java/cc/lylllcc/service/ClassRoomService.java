package cc.lylllcc.service;

import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import cc.lylllcc.util.SzsdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomService {

    @Autowired
    private UserRepository userRepository;


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

        String szsdCookie = SzsdUtils.getSzsdCookie(stuid,stdpwd);
        String jwxtCookie = SzsdUtils.getJwxtCookie(szsdCookie);


        return 1;
    }
}
