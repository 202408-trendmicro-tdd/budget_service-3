package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Budget budget) {
        LocalDate overlappingStart = startDate.isAfter(budget.firstDay()) ? startDate : budget.firstDay();
        LocalDate overlappingEnd = endDate.isBefore(budget.lastDay()) ? endDate : budget.lastDay();
        if (budget.getYearMonth().equals(YearMonth.from(startDate()))) {
//            overlappingStart = startDate();
//            overlappingEnd = budget.lastDay();
        } else if (budget.getYearMonth().equals(YearMonth.from(endDate()))) {
//            overlappingStart = budget.firstDay();
//            overlappingEnd = endDate();
        } else if (budget.getYearMonth().isAfter(YearMonth.from(startDate())) && budget.getYearMonth().isBefore(YearMonth.from(endDate()))) {
//            overlappingStart = budget.firstDay();
//            overlappingEnd = budget.lastDay();
        }
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}