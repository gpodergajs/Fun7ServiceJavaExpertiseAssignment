package com;

import com.gpode.SpringBootRestApplication;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringBootRestApplication.class})
public class SpringContextTest {

    @Test
    public void contextLoads() {
    }

}
