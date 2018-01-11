package cc.lylllcc.dto;

public class  Use{

    private String invest;
    private String fixed;
    private String sleep;
    private String waste;

    public Use() {
    }

    public Use(String invest, String fixed, String sleep, String waste) {
        this.invest = invest;
        this.fixed = fixed;
        this.sleep = sleep;
        this.waste = waste;
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
