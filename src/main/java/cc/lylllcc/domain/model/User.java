package cc.lylllcc.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

/**
 * Created by lylllcc on 2017/2/23.
 */

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"password", "id"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String password;
    private String username;
    private double grade = 0.0;
    private String studentId;
    private String phone;
    private String ipassword;

    private Boolean isRemind = false;

    public Boolean getRemind() {
        return isRemind;
    }

    public void setRemind(Boolean remind) {
        isRemind = remind;
    }

    public String getIpassword() {
        return ipassword;
    }

    public void setIpassword(String ipassword) {
        this.ipassword = ipassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public User(String password, String username, String phone) {
        this.password = password;
        this.username = username;
        this.phone = phone;
    }

    public User() {
    }
}
