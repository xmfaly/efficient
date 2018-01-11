package cc.lylllcc.service;

import cc.lylllcc.dto.ClassRoom;
import cc.lylllcc.dto.Grade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockService {

    public Object mockgrade(){
        List<Grade> list = new ArrayList<>();
        Grade grade = new Grade("军训","90","2");
        list.add(grade);

        grade = new Grade("大学英语(4-1)","70","3");
        list.add(grade);

        grade = new Grade("军事理论","89","2");
        list.add(grade);

        grade = new Grade("中国近代史纲要","80","3");
        list.add(grade);

        grade = new Grade("计算概论","79","2");
        list.add(grade);

        grade = new Grade("歌剧舞剧赏析","79","2");
        list.add(grade);

        grade = new Grade("高等数学（2-1）","90","5.5");
        list.add(grade);

        grade = new Grade("技术经济学","88","2");
        list.add(grade);

        grade = new Grade("高等数学（2-2）","89","5");
        list.add(grade);

        grade = new Grade("大学英语(4-1)","80","4");
        list.add(grade);

        return list;
    }

    public Object mockclass(){
        List<ClassRoom> list = new ArrayList<>();

        List<String> num = new ArrayList<>();
        num.add("101");
        num.add("107");
        num.add("110");
        num.add("201");
        num.add("220");
        num.add("311");
        num.add("507");
        ClassRoom classRoom = new ClassRoom("南教",num);
        list.add(classRoom);

        num = new ArrayList<>();
        num.add("101");
        num.add("102");
        num.add("111");
        num.add("201");
        num.add("221");
        num.add("311");
        classRoom = new ClassRoom("南堂",num);
        list.add(classRoom);


        num = new ArrayList<>();
        num.add("106");
        num.add("107");
        num.add("201");
        num.add("205");
        num.add("307");
        classRoom = new ClassRoom("东环",num);
        list.add(classRoom);

        num = new ArrayList<>();
        num.add("105");
        num.add("106");
        num.add("201");
        num.add("204");
        num.add("306");
        classRoom = new ClassRoom("西环",num);
        list.add(classRoom);

        num = new ArrayList<>();
        num.add("102");
        num.add("103");
        num.add("201");
        classRoom = new ClassRoom("东廊",num);
        list.add(classRoom);

        num = new ArrayList<>();
        num.add("201");
        num.add("202");
        num.add("303");
        classRoom = new ClassRoom("西廊",num);
        list.add(classRoom);

        return list;
        
    }
}
