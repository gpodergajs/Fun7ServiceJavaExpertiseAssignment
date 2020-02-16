package com.integration;


import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.gpode.SpringBootRestApplication;
import com.gpode.controllers.CheckServicesController;
import com.gpode.enums.Service;
import com.gpode.enums.Status;
import com.gpode.services.*;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
public class CheckServiceStatusControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private MultiplayerServiceImpl mutliplayerService;

    @Mock
    private UserSupportServiceImpl userSupportService;

    @Mock
    UserServiceImpl userService;

    @Mock
    AdsServiceImpl adsService;

    @InjectMocks
    private CheckServicesController checkServicesController;

    private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    private OkHttpClient httpClient;



    @Before
    public void preTest(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(checkServicesController)
                .build();

        queryParams.set("timezone","Europe/Ljubljana");
        queryParams.set("userId","1");
        queryParams.set("cc","SVN");

        httpClient = new OkHttpClient.Builder().build();

    }


    @Test
    public void controllerTest() throws Exception {
        mockMvc.perform(get("/api/serviceStatus")
                .queryParams(queryParams))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void responseBodyTest() throws Exception {

        MvcResult res = mockMvc.perform(get("/api/serviceStatus")
                .queryParams(queryParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        DateTime dt = new DateTime(queryParams.get("timezone"));
        DateTime dtAfter = new DateTime("Europe/Ljubljana").withTimeAtStartOfDay().plusHours(9);
        DateTime dtBefore = new DateTime("Europe/Ljubljana").withTimeAtStartOfDay().plusHours(15);


        JSONObject responseJson = new JSONObject();
        responseJson.put(Service.MULTIPLAYER.label, Status.DISABLED.label);
        responseJson.put(Service.USER_SUPPORT.label, dt.isAfter(dtAfter) && dt.isBefore(dtBefore) ? Status.ENABLED.label : Status.DISABLED.label);
        responseJson.put(Service.ADS.label, Status.ENABLED.label);

        Assert.assertEquals(responseJson.toString(), res.getResponse().getContentAsString());


    }

    @Test
    public void requestBodyTest(){

    }




}