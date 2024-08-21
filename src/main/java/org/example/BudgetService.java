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


        double totalAmount = 0;
        Period period = new Period(startDate, endDate);
        for (Budget budget : budgets) {
            long overlappingDays = period.getOverlappingDays(budget.createPeriod());
            totalAmount += budget.dailyAmount() * overlappingDays;
        }

        return totalAmount;
    }


}
