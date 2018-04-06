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

    }
}
