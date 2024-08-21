package org.example;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    public String yearMonth;
    public double amount;

    public Budget(String yearMonth, double amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public YearMonth getYearMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }
}
