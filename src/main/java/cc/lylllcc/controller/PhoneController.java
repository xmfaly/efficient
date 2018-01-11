package cc.lylllcc.controller;

import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class PhoneController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/changephone")
    public Object change(String username,String phone){
        User user = userRepository.findFirstByUsername(username);
        if(user == null){
            return new JsonMes(-1,"用户名不存在");
        }
        user.setPhone(phone);
        userRepository.save(user);
        return new JsonMes(1,"修改成功");
    }
}
