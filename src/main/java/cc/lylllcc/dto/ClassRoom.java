package cc.lylllcc.dto;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    public String room;
    public List<String> num = new ArrayList<>();

    public ClassRoom() {
    }

    public ClassRoom(String room, List<String> num) {
        this.room = room;
        this.num = num;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getNum() {
        return num;
    }

    public void setNum(List<String> num) {
        this.num = num;
    }
}
