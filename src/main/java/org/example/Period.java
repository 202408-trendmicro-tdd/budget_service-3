package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Budget budget) {
        Period another = new Period(budget.firstDay(), budget.lastDay());
        LocalDate overlappingStart = startDate.isAfter(another.startDate) ? startDate : another.startDate;
        LocalDate overlappingEnd = endDate.isBefore(another.endDate) ? endDate : another.endDate;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}