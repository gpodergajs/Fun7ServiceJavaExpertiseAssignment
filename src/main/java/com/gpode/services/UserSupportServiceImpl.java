package com.gpode.services;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.gpode.utils.DateUtils.IsBetweenLocalTimeZoneInterval;

@Service
public class UserSupportServiceImpl implements UserSupportService {

    private final ZoneId currZoneId = ZoneId.of("Europe/Ljubljana");

    public boolean getUserSupportServiceStatus(String reqTimezone) {
            return IsBetweenLocalTimeZoneInterval(reqTimezone);
    }
}
