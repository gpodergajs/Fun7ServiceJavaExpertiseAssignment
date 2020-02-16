package com.gpode.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.gson.JsonObject;
import com.gpode.Model.User;
import com.gpode.enums.QueryParameter;
import com.gpode.enums.Service;
import com.gpode.enums.Status;
import com.gpode.services.AdsService;
import com.gpode.services.MultiplayerService;
import com.gpode.services.UserService;
import com.gpode.services.UserSupportService;

import org.json.JSONObject;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class CheckServicesController {


    @Autowired
    MultiplayerService multiplayerService;

    @Autowired
    AdsService adsService;

    @Autowired
    UserSupportService userSupportService;

    @Autowired
    UserService userService;


    @RequestMapping(value="/servicesStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetServicesStatus(
            @RequestParam("timezone") String timezone,
            @RequestParam("userId") String userId,
            @RequestParam("cc") String cc
    ){
        //DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        HashMap<String,String> queryMap = new HashMap<String,String>();
        queryMap.put(QueryParameter.TIMEZONE.label, timezone);
        queryMap.put(QueryParameter.CC.label, cc);
        queryMap.put(QueryParameter.USER_ID.label, userId);

        // we check if the params are null or empty
        if(queryMap.containsValue(null) || queryMap.containsValue("")){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Custom error message");
        }

        try {

            // check if user exists
            User user = userService.getUserById(Long.parseLong(userId));

            // if it exists we increment the api call property (relevant for the multiplayer service
            if (user != null) {
                System.out.println(user.getApiCalls());
                int apiCalls = user.getApiCalls() + 1;
                user.setApiCalls(apiCalls);
                System.out.println(user.getApiCalls());
                userService.updateUser(user);

            } else {
                // if it doesnt exist we create and save a new one
                user = new User();
                user.setUserId(Long.parseLong(userId));
                user.setApiCalls(1);;
                userService.insertUser(user);
            }

            boolean isMultiplayerServiceAvailable = multiplayerService.getMultiplayerServiceStatus(user.getApiCalls());
            boolean isAdsServiceAvailable = adsService.getAdsServiceStatus(cc);
            boolean isUserSupportServiceAvailable = userSupportService.getUserSupportServiceStatus();

            JSONObject jObj = new JSONObject();
            jObj.put(Service.ADS.label, isAdsServiceAvailable ? Status.ENABLED.label : Status.DISABLED.label);
            jObj.put(Service.MULTIPLAYER.label, isMultiplayerServiceAvailable ? Status.ENABLED.label : Status.DISABLED.label);
            jObj.put(Service.USER_SUPPORT.label, isUserSupportServiceAvailable ? Status.ENABLED.label : Status.DISABLED.label);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jObj.toString());

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
