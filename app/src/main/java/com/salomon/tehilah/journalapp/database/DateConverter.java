package com.salomon.tehilah.journalapp.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Salomon KABONGO on 30-Jun-18.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

