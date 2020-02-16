package com.gpode.Dao;

import com.gpode.Model.User;
import com.gpode.services.objecitfy.OfyService;

public class UserDao {

    public static void Insert(User user){
        OfyService.ofy().save().entity(user).now();
    }

    public static User ReadById(long id){
        return OfyService.ofy().load().type(User.class).id(id).now();
    }

    public static void Update(User user){
        OfyService.ofy().save().entity(user).now();
    }

}
