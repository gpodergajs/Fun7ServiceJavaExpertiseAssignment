package com.functional;

import com.gpode.Model.User;
import com.gpode.SpringBootRestApplication;
import com.gpode.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRestApplication.class)
public class ServicesTest {

    @Mock
    private AdsServiceImpl adsService;

    @Mock
    private MultiplayerServiceImpl multiplayerSevice;

    @Mock
    private UserSupportServiceImpl userSupportService;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void preTest(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void AdsServiceTest(){
        boolean status = adsService.getAdsServiceStatus("SVN");
        Assert.assertEquals(true, status);
    }

    @Test
    public void MutliplayerServiceTest(){
        boolean status = multiplayerSevice.getMultiplayerServiceStatus(1);
        Assert.assertEquals(status, false);

        status = multiplayerSevice.getMultiplayerServiceStatus(6);
        Assert.assertEquals(status, true);

    }

    @Test
    public void UserSupportServiceTest(){

    }

    @Test
    public void UserServiceTest(){
        User user = new User();
        user.setUserId(1000L);
        user.setApiCalls(1);;
        userService.insertUser(user);

        user = userService.getUserById(1000L);
        Assert.assertNotNull(user);


    }


}
