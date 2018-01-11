package cc.lylllcc.domain.model;

import cc.lylllcc.dto.Use;

import javax.persistence.*;

@Entity
@Table(name = "use_model")
public class UseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int timeUseid = 0;

    private String invest;
    private String fixed;
    private String sleep;
    private String waste;


    public UseModel(Use use) {
        this.invest = use.getInvest();
        this.fixed = use.getFixed();
        this.sleep = use.getSleep();
        this.waste = use.getWaste();
    }

    public UseModel() {
    }

    public int getTimeUseid() {
        return timeUseid;
    }

    public void setTimeUseid(int timeUseid) {
        this.timeUseid = timeUseid;
    }

    public UseModel(String invest, String fixed, String sleep, String waste) {
        this.invest = invest;
        this.fixed = fixed;
        this.sleep = sleep;
        this.waste = waste;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvest() {
        return invest;
    }

    public void setInvest(String invest) {
        this.invest = invest;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }
}
