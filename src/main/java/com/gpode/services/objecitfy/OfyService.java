package com.gpode.services.objecitfy;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.gpode.Model.User;

public class OfyService {
    static {
        ObjectifyService.register(User.class);
    }
    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory(){
        return ObjectifyService.factory();
    }

}
