package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Budget budget) {
        LocalDate overlappingStart = startDate.isAfter(budget.firstDay()) ? startDate : budget.firstDay();
        LocalDate overlappingEnd = endDate.isBefore(budget.lastDay()) ? endDate : budget.lastDay();
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}