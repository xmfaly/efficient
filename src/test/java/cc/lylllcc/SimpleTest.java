package cc.lylllcc;

import cc.lylllcc.szsdmodel.JwxtScore;
import cc.lylllcc.util.SzsdUtils;
import org.junit.Test;

import java.util.List;

public class SimpleTest {

    @Test
    public void simpleTest() throws Exception {
        String szsdCookie = SzsdUtils.getSzsdCookie("1507020129","xmfly1314");
        String jwxtCookie = SzsdUtils.getJwxtCookie(szsdCookie);
        List<JwxtScore> scores =  SzsdUtils.getScore("",jwxtCookie);

        System.out.println(scores);
    }
}
