package org.example;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate startDate, LocalDate endDate) {
    long getOverlappingDays(Period another) {
        LocalDate overlappingStart = startDate.isAfter(another.startDate) ? startDate : another.startDate;
        LocalDate overlappingEnd = endDate.isBefore(another.endDate) ? endDate : another.endDate;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}