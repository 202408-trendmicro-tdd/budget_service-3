package org.example;

import java.time.LocalDate;
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


        double totalAmount = 0;
        Period period = new Period(startDate, endDate);
        for (Budget budget : budgets) {
            totalAmount += budget.overlappingAmount(period);
        }

        return totalAmount;
    }


}
