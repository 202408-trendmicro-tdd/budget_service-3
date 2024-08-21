package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Budget budget) {
        Period another = new Period(budget.firstDay(), budget.lastDay());
        LocalDate firstDay = another.startDate;
        LocalDate lastDay = another.endDate;
        LocalDate overlappingStart = startDate.isAfter(firstDay) ? startDate : firstDay;
        LocalDate overlappingEnd = endDate.isBefore(lastDay) ? endDate : lastDay;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}