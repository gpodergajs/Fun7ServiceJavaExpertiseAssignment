package com.gpode.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {



    public static boolean IsBetweenLocalTimeZoneInterval(String reqTimezone){
        ZoneId currZoneId = ZoneId.of("Europe/Ljubljana");
        ZoneId foreignZone = ZoneId.of(reqTimezone);

        Instant instant = Instant.now();
        ZonedDateTime currentTime = instant.atZone(currZoneId);
        ZonedDateTime foreignTime = instant.atZone(foreignZone);

        ZonedDateTime startInterval = currentTime.toLocalDate().atStartOfDay(currZoneId).plusHours(9);
        ZonedDateTime endInterval = currentTime.toLocalDate().atStartOfDay(currZoneId).plusHours(15);

        // if between 9 and 15
        return foreignTime.isAfter(startInterval) && foreignTime.isBefore(endInterval);

    }

}
