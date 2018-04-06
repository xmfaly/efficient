package cc.lylllcc;

import cc.lylllcc.domain.model.User;
import cc.lylllcc.domain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() throws Exception {
//        String szsdCookie = SzsdUtils.getSzsdCookie("1507020129","xmfly1314");
//
//        System.out.println(szsdCookie);
//        String jwxtCookie = SzsdUtils.getJwxtCookie(szsdCookie);
//
//        System.out.println(jwxtCookie);

       // List<JwxtScore> scores =  SzsdUtils.getScore();

        User user = new User(new BCryptPasswordEncoder().encode("test"), "test", "110");

        userRepository.save(user);
    }
}
