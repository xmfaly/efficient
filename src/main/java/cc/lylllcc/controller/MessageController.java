package cc.lylllcc.controller;

import cc.lylllcc.domain.model.Message;
import cc.lylllcc.domain.repository.MessageRepository;
import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import cc.lylllcc.service.SendMessageService;
import cc.lylllcc.service.TestMessage;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TestMessage testMessage;

    @RequestMapping(value = {"/regist"},method = RequestMethod.POST)
    public Object regist(String phone, String username) throws ClientException {

        //检测是否已经注册
        User user = userRepository.findFirstByPhone(phone);
        if (user != null) {
            return new JsonMes(-1, "手机已经被注册");
        }
        user = userRepository.findFirstByUsername(username);
        if (user != null) {
            return new JsonMes(-1, "用户名已经被注册");
        }


        String code = sendMessageService.gencode();
        String mes = sendMessageService.sendMessage(phone, code);
        if (mes.equals("OK") == false) {
            return new JsonMes(-1, mes);
        }

        //删除已经有的验证码 防止查找异常
        Iterable<Message> messages = messageRepository.findByUsername(username);
        Iterator<Message> messageIterator = messages.iterator();
        while(messageIterator.hasNext()){
            Message message = messageIterator.next();
            messageRepository.delete(message);
        }

        Message message = new Message(username,code);
        messageRepository.save(message);
        return new JsonMes(1, "发送成功");
    }

    @RequestMapping(value = {"/changephone"},method = RequestMethod.POST)
    public Object changephone(String phone, String username) throws ClientException {

        String code = sendMessageService.gencode();
        String mes = sendMessageService.sendMessage(phone, code);
        if (mes.equals("OK") == false) {
            return new JsonMes(-1, mes);
        }

        //删除已经有的验证码 防止查找异常
        Iterable<Message> messages = messageRepository.findByUsername(username);
        Iterator<Message> messageIterator = messages.iterator();
        while(messageIterator.hasNext()){
            Message message = messageIterator.next();
            messageRepository.delete(message);
        }

        Message message = new Message(username,code);
        messageRepository.save(message);
        return new JsonMes(1, "发送成功");
    }


    @GetMapping("/test")
    public Object test(String username,String code){
        return testMessage.testMessage(username,code);
    }

    @PostMapping("/forget")
    public Object forget(String username) throws ClientException {
        User user = userRepository.findFirstByUsername(username);
        if(user == null){
            return new JsonMes(-1,"用户不存在");
        }
        String phone = user.getPhone();
        if(phone == null){
            return new JsonMes(-1,"用户没有绑定手机号");
        }


        String code = sendMessageService.gencode();
        String mes = sendMessageService.sendMessage(phone, code);
        if (mes.equals("OK") == false) {
            return new JsonMes(-1, mes);
        }

        //删除已经有的验证码 防止查找异常
        Iterable<Message> messages = messageRepository.findByUsername(username);
        Iterator<Message> messageIterator = messages.iterator();
        while(messageIterator.hasNext()){
            Message message = messageIterator.next();
            messageRepository.delete(message);
        }

        Message message = new Message(username,code);
        messageRepository.save(message);
        return new JsonMes(1, "发送成功");
    }

}
