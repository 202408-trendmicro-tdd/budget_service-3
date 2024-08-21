package org.example;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {

    private final IRepository BudgetRepo;


    public BudgetService(IRepository BudgetRepo) {
        this.BudgetRepo = BudgetRepo;
    }

    public double totalAmount(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return 0;
        }


        Period period = new Period(startDate, endDate);

        return BudgetRepo.getAll().stream().mapToDouble(budget -> budget.overlappingAmount(period)).sum();
    }


}
