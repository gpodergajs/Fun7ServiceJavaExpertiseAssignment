package com.gpode.services;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

@Service
public class UserSupportServiceImpl implements UserSupportService {

    private final DateTimeZone timeZone = DateTimeZone.forID("Europe/Ljubljana");

    public boolean getUserSupportServiceStatus() {
         DateTime startInterval = new DateTime(timeZone).plusHours(9);
         DateTime endInterval = new DateTime(timeZone).plusHours(15);
         DateTime currentTime = new DateTime(timeZone);

         // if service was contacted between 9 and 15 o'clock Ljubljana time
         return currentTime.isAfter(startInterval) && currentTime.isBefore(endInterval) ? true : false;
    }
}
