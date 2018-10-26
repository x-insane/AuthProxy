package com.xinsane.auth.account.test;

import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testHasAuth() {
        System.out.println(userRepository.hasAuth(1, "login"));
    }

    @Test
    public void testSave() {
        System.out.println(userRepository.save(new UserEntity().setUsername("xinsane2").setPassword("xxx")));
    }

    @Test
    public void testGetUserAuth() {
        System.out.println(userRepository.getUserAuth(1));
    }

}
