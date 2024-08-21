package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetService {

    private final IRepository BudgetRepo;

    List<Budget> budgets;

    public BudgetService(IRepository BudgetRepo) {
        this.BudgetRepo = BudgetRepo;
    }

    private static long getOverlappingDays(LocalDate startDate, LocalDate endDate, Budget budget) {
        LocalDate overlappingStart = null;
        LocalDate overlappingEnd = null;
        if (budget.getYearMonth().equals(YearMonth.from(startDate))) {
            overlappingStart = startDate;
            overlappingEnd = budget.lastDay();
        } else if (budget.getYearMonth().equals(YearMonth.from(endDate))) {
            overlappingStart = budget.firstDay();
            overlappingEnd = endDate;
        } else if (budget.getYearMonth().isAfter(YearMonth.from(startDate)) && budget.getYearMonth().isBefore(YearMonth.from(endDate))) {
            overlappingStart = budget.firstDay();
            overlappingEnd = budget.lastDay();
        }
        long overlappingDays = DAYS.between(overlappingStart, overlappingEnd) + 1;
        return overlappingDays;
    }

    public double totalAmount(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return 0;
        }

        budgets = BudgetRepo.getAll();
        if (this.budgets.isEmpty()) {
            return 0;
        }

        YearMonth startYearMonth = YearMonth.from(startDate);
        YearMonth endYearMonth = YearMonth.from(endDate);
        if (startYearMonth.equals(endYearMonth)) {
            long overlappingDays = DAYS.between(startDate, endDate) + 1;

            for (Budget budget : budgets) {
                if (budget.getYearMonth().equals(startYearMonth)) {
                    return budget.dailyAmount() * overlappingDays;
                }
            }
            return 0;
        }

        double totalAmount = 0;
        for (Budget budget : budgets) {
            long overlappingDays = getOverlappingDays(startDate, endDate, budget);
            totalAmount += budget.dailyAmount() * overlappingDays;
        }

        return totalAmount;
    }


}
