package com.xinsane.auth.account.test;

import com.xinsane.auth.account.repository.AuthRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    public void testGetAuthIdByAuthKey() {
        System.out.println(authRepository.getAuthIdByAuthKey("logi2n"));
    }

}
