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

        int startDayDiff = startDate.lengthOfMonth() - startDate.getDayOfMonth() + 1;
        int endDayDiff = endDate.getDayOfMonth();

        double totalAmount = 0;
        for (Budget budget : budgets) {
            if (budget.getYearMonth().equals(startYearMonth)) {
                totalAmount += budget.dailyAmount() * startDayDiff;
            } else if (budget.getYearMonth().equals(endYearMonth)) {
                totalAmount += budget.dailyAmount() * endDayDiff;
            } else {
                if (budget.getYearMonth().isAfter(startYearMonth) && budget.getYearMonth().isBefore(endYearMonth)) {
                    totalAmount += budget.amount;
                }
            }
        }

        return totalAmount;
    }


}
