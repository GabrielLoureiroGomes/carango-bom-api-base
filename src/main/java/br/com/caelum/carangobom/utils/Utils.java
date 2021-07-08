package br.com.caelum.carangobom.utils;

import java.time.LocalDate;

public final class Utils {

    private Utils() {
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

}