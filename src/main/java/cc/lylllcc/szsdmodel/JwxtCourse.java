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
    private String courseName;
    private String classRoom;
    private String teacherName;
    private int day;//周日到周六 0-6
    private int beginLesson;//开始节数
    private int endLesson; //结束节数
    private int beginWeek; //开始周数
    private int endWeek; //结束周数
    private int courseType; //1单周，2双周,3有特殊周
    private Set<Integer> expected;

    public JwxtCourse() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public JwxtCourse(String courseName) {
        this.courseName = courseName;
        this.expected = new HashSet();
    }

    public void addWeek(int week) {
        this.expected.add(week);
    }

    public boolean isThisWeek(int week) {
        return this.expected.contains(week);
    }
}
