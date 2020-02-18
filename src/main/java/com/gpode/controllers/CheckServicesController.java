package com.gpode.controllers;

import com.gpode.Model.User;
import com.gpode.constants.QueryParamConsts;
import com.gpode.constants.ServiceConsts;
import com.gpode.constants.StatusConsts;
import com.gpode.enums.QueryParameter;
import com.gpode.services.AdsService;
import com.gpode.services.MultiplayerService;
import com.gpode.services.UserService;
import com.gpode.services.UserSupportService;

import org.json.JSONObject;
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


        HashMap<String,String> queryMap = new HashMap<String,String>();
        queryMap.put(QueryParamConsts.TIMEZONE, timezone);
        queryMap.put(QueryParamConsts.CC, cc);
        queryMap.put(QueryParamConsts.USER_ID, userId);

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
                //System.out.println(user.getApiCalls());
                int apiCalls = user.getApiCalls() + 1;
                user.setApiCalls(apiCalls);
                //System.out.println(user.getApiCalls());
                userService.updateUser(user);

            } else {
                // if it doesnt exist we create and save a new one
                user = new User();
                user.setUserId(Long.parseLong(userId));
                user.setApiCalls(1);;
                userService.insertUser(user);
            }

            // we get the status and build a response entity
            boolean isMultiplayerServiceAvailable = multiplayerService.getMultiplayerServiceStatus(user.getApiCalls());
            boolean isAdsServiceAvailable = adsService.getAdsServiceStatus(cc);
            boolean isUserSupportServiceAvailable = userSupportService.getUserSupportServiceStatus(timezone);

            JSONObject jObj = new JSONObject();
            jObj.put(ServiceConsts.ADS, isAdsServiceAvailable ? StatusConsts.ENABLED : StatusConsts.DISABLED);
            jObj.put(ServiceConsts.MULTIPLAYER, isMultiplayerServiceAvailable ? StatusConsts.ENABLED : StatusConsts.DISABLED);
            jObj.put(ServiceConsts.USER_SUPPORT, isUserSupportServiceAvailable ? StatusConsts.ENABLED : StatusConsts.DISABLED);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jObj.toString());

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.toString());

        }
    }

}
