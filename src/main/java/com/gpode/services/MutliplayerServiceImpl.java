package com.gpode.services;

import org.springframework.stereotype.Service;

@Service
public class MutliplayerServiceImpl implements MultiplayerService {

    public boolean getMultiplayerServiceStatus(int apiCalls) {
        return apiCalls >= 5 ? true : false;
    }
}
