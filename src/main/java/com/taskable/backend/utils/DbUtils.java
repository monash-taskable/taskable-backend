package com.taskable.backend.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DbUtils {
    public static LocalDateTime getDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }
}
