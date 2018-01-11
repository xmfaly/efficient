package cc.lylllcc.domain.model;

import cc.lylllcc.dto.TimeUse;
import cc.lylllcc.dto.Use;

import javax.persistence.*;

@Entity
@Table(name = "time_use")
public class TimeUseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int finishNum;
    private int undoneNum;
    private int totalNum;
    private String date;
    private String username;


    public TimeUseModel(TimeUse timeUse) {
        this.finishNum = timeUse.getFinishNum();
        this.undoneNum = timeUse.getUndoneNum();
        this.totalNum = timeUse.getTotalNum();
        this.date = timeUse.getDate();
        this.username = timeUse.getUsername();
    }

    public TimeUseModel() {
    }

    public TimeUseModel(int finishNum, int undoneNum, int totalNum, String date, String username) {
        this.finishNum = finishNum;
        this.undoneNum = undoneNum;
        this.totalNum = totalNum;
        this.date = date;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public int getUndoneNum() {
        return undoneNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }

    public void setUndoneNum(int undoneNum) {
        this.undoneNum = undoneNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
