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

    double dailyAmount() {
        return this.amount / days();
    }

    public YearMonth getYearMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public double days() {
        return getYearMonth().lengthOfMonth();
    }
}
