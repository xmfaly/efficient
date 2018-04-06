package cc.lylllcc.controller;
import cc.lylllcc.service.MockService;
import cc.lylllcc.service.XzsdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stu")
public class StuController {

    @Autowired
    private MockService mockService;

    @Autowired
    private XzsdService xzsdService;


    @GetMapping("/grade")
    public Object grade(String username) throws Exception {
        return xzsdService.getScore(username);
    }

    @GetMapping("/classroom")
    public Object classroom(String date,String n){
        return mockService.mockclass();
    }
}
