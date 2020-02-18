package com.unit;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.gpode.Model.User;
import com.gpode.SpringBootRestApplication;
import com.gpode.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRestApplication.class)
public class ServiceTest {

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
        Assert.assertEquals(false, status);
    }

    @Test
    public void MultiplayerServiceTest(){
        boolean expectedFalse = multiplayerSevice.getMultiplayerServiceStatus(1);
        Assert.assertFalse(expectedFalse);

        boolean expectedTrue = multiplayerSevice.getMultiplayerServiceStatus(10);
        Assert.assertTrue(expectedTrue);
    }

    @Test
    public void UserSupportServiceTest(){
        boolean supportStatus = userSupportService.getUserSupportServiceStatus("Pacific/Honolulu");
        ZoneId currZoneId = ZoneId.of("Europe/Ljubljana");
        Instant instant = Instant.now();
        ZonedDateTime currentTime = instant.atZone(currZoneId);

        if(currentTime.getHour() > 9 && currentTime.getHour() < 15){
            Assert.assertTrue(supportStatus);
        }else
            Assert.assertFalse(supportStatus);
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
