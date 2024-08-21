package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Budget budget) {
        LocalDate firstDay = budget.firstDay();
        LocalDate lastDay = budget.lastDay();
        LocalDate overlappingStart = startDate.isAfter(firstDay) ? startDate : firstDay;
        LocalDate overlappingEnd = endDate.isBefore(lastDay) ? endDate : lastDay;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}