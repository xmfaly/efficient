package cc.lylllcc.szsdmodel;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xhaiben
 * @date 2016/8/28
 */
@Data
public class JwxtCourse implements Serializable {
    private String name;
    private String classroom;
    private String teacher;
    private int dayOfWeek;//周日到周六 0-6
    private int timeStart;//开始节数
    private int timeEnd; //结束节数
    private int weekStart; //开始周数
    private int weekEnd; //结束周数

    public JwxtCourse(){

    }
    public JwxtCourse(String name){
        this.name = name;
    }

}
