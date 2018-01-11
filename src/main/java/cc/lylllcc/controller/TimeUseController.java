package cc.lylllcc.controller;


import cc.lylllcc.dto.TimeUse;
import cc.lylllcc.dto.Use;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/time")
public class TimeUseController {

    @PostMapping("/analysis")
    public Object analysis(@RequestBody TimeUse timeUse){
        String s1 = "太棒了，再接再厉";
        String s2 = "";
        int finishNum = timeUse.getFinishNum();
        int totalNum = timeUse.getTotalNum();
        if(totalNum == 0){
            s2 = "您的总完成数数为0无法分析";
        }else {
            double per = finishNum * 1.0 / totalNum;
            if(per < 0.8){
                s2 = "目标完成率很低，仍需努力";
            }else if(per<1){
                s2 = "再加把劲，你就可以完成所有目标了";
            }else {
                s2 = "太棒了，再接再厉";
            }
        }
        Use[] uses = timeUse.getUse();
        double invest = 0;
        double fixed = 0;
        double sleep = 0;
        double waste = 0;

        for(int i=0;i<uses.length;i++) {
            invest += Double.valueOf(uses[i].getInvest());
            fixed += Double.valueOf(uses[i].getFixed());
            sleep += Double.valueOf(uses[i].getSleep());
            waste += Double.valueOf(uses[i].getWaste());
        }

        if(invest < waste){
            s1 = "浪费时间很多！";
        }else if(invest ==0 ){
            s1 = "投资数为0，请合理利用时间";
        }else {
            s1 = "仍需努力!";
        }

        Map<String,String> map = new HashMap<>();
        map.put("s1",s1);
        map.put("s2",s2);
        return map;
    }


    @PostMapping("/analysistest")
    public Object analysisTest(@RequestBody TimeUse timeUse){
        return timeUse;
    }
}
