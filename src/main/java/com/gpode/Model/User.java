package com.gpode.Model;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
    @Id
    private Long userId;
    private int apiCalls;

    public User(){}

    public User(Long userId, int apiCalls) {
        this.userId = userId;
        this.apiCalls = apiCalls;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setApiCalls(int apiCalls){
        this.apiCalls = apiCalls;
    }

    public int getApiCalls(){
        return apiCalls;
    }

}
