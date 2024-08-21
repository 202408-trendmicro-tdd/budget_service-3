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
            long overlappingDays = 0;
            if (budget.getYearMonth().equals(startYearMonth)) {
                overlappingDays = DAYS.between(startDate, budget.lastDay()) + 1;
            } else if (budget.getYearMonth().equals(endYearMonth)) {
                overlappingDays = DAYS.between(budget.firstDay(), endDate) + 1;
//                overlappingDays = endDate.getDayOfMonth();
            } else if (budget.getYearMonth().isAfter(startYearMonth) && budget.getYearMonth().isBefore(endYearMonth)) {
                overlappingDays = budget.days();
            }
            totalAmount += budget.dailyAmount() * overlappingDays;
        }

        return totalAmount;
    }


}
