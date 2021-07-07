package br.com.caelum.carangobom.utils;

import java.time.LocalDate;

public class Utils {

    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }
}