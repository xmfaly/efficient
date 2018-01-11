package cc.lylllcc.service;

import cc.lylllcc.domain.model.Message;
import cc.lylllcc.domain.repository.MessageRepository;
import cc.lylllcc.dto.JsonMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TestMessage {

    @Autowired
    private MessageRepository messageRepository;

    public Object testMessage(String username,String code){
        Collection<Message> messages = messageRepository.findByUsernameAndCode(username,code);
        if(!messages.isEmpty()){
            return new JsonMes(1,"验证成功");
        }
        return new JsonMes(-1,"验证码错误");
    }
}
