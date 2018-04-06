package cc.lylllcc.controller;

import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import cc.lylllcc.service.RequestService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lylllcc on 2017/2/23.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    public static String server = "http://115.159.198.70/SuperScholar/user/";

    @Autowired
    private RequestService requestService;

    @GetMapping("/userinfo")
    @JsonIgnore
    public Object info(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @PostMapping("/regist")
    @JsonIgnore
    public Object regist(String username, String password, String phone) {
        if (userRepository.findByUsername(username).isEmpty() != true) {
            return new JsonMes(-1, "用户名已存在");
        } else if (userRepository.findByPhone(phone).isEmpty() != true) {
            return new JsonMes(-1, "该电话已经被注册");
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            try {
                User user = new User(passwordEncoder.encode(password), username, phone);

//                String url = server + "register";
//                String param = "username=" + username + "&phone=" + phone;
//                requestService.get(url, param);

                user = userRepository.save(user);
                Map<String,Object> map = new HashMap<>();
                map.put("code",1);
                map.put("message","注册成功");
                map.put("user",user);
                return map;
            } catch (Exception ex) {
                return new JsonMes(-1, "未知错误");
            }
        }

    }

    @PostMapping("/login")
    public Object login(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return new JsonMes(1, "用户不存在");
        } else if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return new JsonMes(2, "用户名或密码错误");
        }

    }

    @PostMapping("/bind")
    public Object bind(String username, String studentId, String ipassword) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return new JsonMes(1, "用户不存在");
        } else {
            try {
                user.setStudentId(studentId);
                user.setIpassword(ipassword);
                userRepository.save(user);
//                String url = server + "bind";
//                String param = "username=" + username + "&sid=" + studentId + "&spwd=" + ipassword;
//                requestService.get(url, param);
                return new JsonMes(0, "绑定成功");
            } catch (Exception ex) {
                return new JsonMes(2, "未知错误");
            }
        }
    }

    @PostMapping("/reset")
    public Object reset(String username, String password, String newpassword) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return new JsonMes(1, "用户不存在");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new JsonMes(2, "密码错误");
        }

        try {
            user.setPassword(passwordEncoder.encode(newpassword));
            userRepository.save(user);
            return new JsonMes(0, "修改成功");
        } catch (Exception ex) {
            return new JsonMes(3, "未知错误");
        }

    }

    @PostMapping("/remind")
    public Object remind(String username, Boolean remind) throws IOException {
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return new JsonMes(-1, "用户不存在");
        }
        user.setRemind(remind);
        userRepository.save(user);
//        String url = server + "remind";
//        String param = "";
//        if (remind == true) {
//            param = "username=" + username + "&method=set";
//        } else {
//            param = "username=" + username + "&method=cancel";
//        }
//        requestService.get(url, param);
        return new JsonMes(1, "设置成功");
    }

    @PostMapping("/changepasswd")
    public Object change(String username, String passwd) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return new JsonMes(-1, "用户不存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(passwd));
        user = userRepository.save(user);
        Map<String,Object> map = new HashMap<>();
        map.put("code",1);
        map.put("message","修改成功");
        map.put("user",user);
        return map;
    }


}

