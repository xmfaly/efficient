package cc.lylllcc.dto;

public class TimeUse {

    public Use[] use;
    private int finishNum;
    private int undoneNum;
    private int totalNum;
    private String date;
    private String username;


    public TimeUse() {
    }

    public TimeUse(Use[] use, int finishNum, int undoneNum, int totalNum, String date, String username) {
        this.use = use;
        this.finishNum = finishNum;
        this.undoneNum = undoneNum;
        this.totalNum = totalNum;
        this.date = date;
        this.username = username;
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

    public Use[] getUse() {
        return use;
    }

    public void setUse(Use[] use) {
        this.use = use;
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
