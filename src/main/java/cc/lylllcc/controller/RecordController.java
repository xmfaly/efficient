package cc.lylllcc.controller;

import cc.lylllcc.domain.model.Record;
import cc.lylllcc.domain.repository.RecordRepository;
import cc.lylllcc.domain.repository.UserRepository;
import cc.lylllcc.dto.JsonMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by lylllcc on 2017/2/25.
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/dorecord")
    public Object record(String startTime, String lastTime, String title, String detail, String species, String username) {
        Record record = new Record(startTime, lastTime, title, detail, species, username);

        if (userRepository.findByUsername(username).isEmpty() == true) {
            return new JsonMes(1, "用户名不存在");
        } else {
            try {
                recordRepository.save(record);
                return new JsonMes(0, "保存成功");
            } catch (Exception ex) {
                return new JsonMes(2, "未知错误");
            }
        }
    }

    @GetMapping("/viewall")
    public Object viewAll(String username) {
        Iterable<Record> records = recordRepository.findByUsername(username);
        if (username == null) {
            return new JsonMes(1, "请输入用户名");
        }
        Collection<Record> re = (Collection<Record>) records;
        if (re.isEmpty()) {
            return new JsonMes(2, "没有事件记录");
        }
        return records;

    }
}
