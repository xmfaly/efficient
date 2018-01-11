package cc.lylllcc.dto;

public class Grade {

    private String KM;
    private String FS;
    private String XF;

    public Grade(String KM, String FS, String XF) {
        this.KM = KM;
        this.FS = FS;
        this.XF = XF;
    }

    public String getKM() {
        return KM;
    }

    public void setKM(String KM) {
        this.KM = KM;
    }

    public String getFS() {
        return FS;
    }

    public void setFS(String FS) {
        this.FS = FS;
    }

    public String getXF() {
        return XF;
    }

    public void setXF(String XF) {
        this.XF = XF;
    }
}
