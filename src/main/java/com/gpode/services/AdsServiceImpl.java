package com.gpode.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import okhttp3.*;

import java.io.IOException;

@Service
public class AdsServiceImpl implements AdsService {

    private OkHttpClient httpClient;
    private final String uri = "https://us-central1-o7tools.cloudfunctions.net/fun7-ad-partner";

    public AdsServiceImpl(){
        InstantiateHttpClient("fun7user","fun7pass");
    }

    public Boolean getAdsServiceStatus(String cc) {
        try {


            Response res = sendGet(uri, cc);
            JSONObject negativeResponse = new JSONObject();
            negativeResponse.put("ads", "you shall not pass!");

            if(!res.isSuccessful() || res.body().equals(negativeResponse))
                return false;

            return true;

        }catch (Exception e){
            return false;
        }
    }

    private void InstantiateHttpClient(String username, String password){
        httpClient = new OkHttpClient.Builder().authenticator(getBasicAuth(username, password)).build();
    }

    private Authenticator getBasicAuth(String username , String password){
        return new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        };
    }

    private Response sendGet(String uri, String cc) throws Exception {
        Request req = new Request.Builder().url(uri+"?countryCode="+cc).build();
        Response resp = httpClient.newCall(req).execute();
        return resp;
    }



}
