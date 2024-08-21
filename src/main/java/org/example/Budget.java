package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class Budget {
    public String yearMonth;
    public double amount;

    public Budget(String yearMonth, double amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }

    double dailyAmount() {
        return this.amount / days();
    }

    public YearMonth getYearMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public int days() {
        return getYearMonth().lengthOfMonth();
    }

    public LocalDate lastDay() {
        return getYearMonth().atEndOfMonth();
    }

    public LocalDate firstDay() {
        return getYearMonth().atDay(1);
    }
}
