package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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
            int days = endDate.lengthOfMonth();
            int overlappingDays = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;

            for (Budget budget : budgets) {
                if (budget.yearMonth.equals(String.format("%d%02d", startDate.getYear(), startDate.getMonthValue()))) {
                    return budget.amount * overlappingDays / days;
                }
            }
            return 0;
        }

        int startDayDiff = startDate.lengthOfMonth() - startDate.getDayOfMonth() + 1;
        int endDayDiff = endDate.getDayOfMonth();

        double totalAmount = 0;
        for (Budget budget : budgets) {
            if (budget.yearMonth.equals(String.format("%d%02d", startDate.getYear(), startDate.getMonthValue()))) {
                totalAmount += budget.amount * startDayDiff / startDate.lengthOfMonth();
            } else if (budget.yearMonth.equals(String.format("%d%02d", endDate.getYear(), endDate.getMonthValue()))) {
                totalAmount += budget.amount * endDayDiff / endDate.lengthOfMonth();
            } else {
                YearMonth bugetDate = YearMonth.of(Integer.valueOf(budget.yearMonth.substring(0, 4)), Integer.valueOf(budget.yearMonth.substring(4)));
                if ((bugetDate.atDay(1).isAfter(startDate) && bugetDate.atDay(1).isBefore(endDate)) &&
                        (bugetDate.atEndOfMonth().isAfter(startDate) && bugetDate.atEndOfMonth().isBefore(endDate))) {
                    totalAmount += budget.amount;
                }
            }
        }


        return totalAmount;


//        return 0;
    }


}
