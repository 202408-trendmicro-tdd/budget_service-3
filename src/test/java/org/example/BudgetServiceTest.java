package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {

    @Mock
    IRepository mockRepo = Mockito.mock(IRepository.class);

    @InjectMocks
    BudgetService budgetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void invalidDate() {
        assertEquals(0, budgetService.totalAmount(LocalDate.now(), LocalDate.MIN));
    }

    @Test
    void noBudgetData() {
        Mockito.when(mockRepo.getAll()).thenReturn(List.of());
        assertEquals(0, budgetService.totalAmount(LocalDate.MIN, LocalDate.MIN));
    }

    @Test
    void getBudgetSameDay() {
        Mockito.when(mockRepo.getAll()).thenReturn(
            List.of(new Budget("202101", 31))
        );

        double actual = budgetService.totalAmount(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 1));
        assertEquals(1, actual);
    }

    @Test
    void getBudgetCrossTwoMonths() {
        Mockito.when(mockRepo.getAll()).thenReturn(
            List.of(
                new Budget("202406", 30),
                new Budget("202407", 31000)
            ));

        double actual = budgetService.totalAmount(LocalDate.of(2024, 6, 30), LocalDate.of(2024, 7, 1));
        assertEquals(1001, actual);
    }

    @Test
    void getBudgetCrossThreeMonths(){
        Mockito.when(mockRepo.getAll()).thenReturn(
            List.of(
                new Budget("202406", 30),
                new Budget("202407", 31000),
                new Budget("202409", 300000)
            ));

        double actual = budgetService.totalAmount(LocalDate.of(2024, 6, 30), LocalDate.of(2024, 9, 1));
        assertEquals(41001, actual);
    }

    @Test
    void getBudgetCrossYear(){
        Mockito.when(mockRepo.getAll()).thenReturn(
            List.of(
                new Budget("202306", 30),
                new Budget("202407", 31000),
                new Budget("202409", 300000)
            ));

        double actual = budgetService.totalAmount(LocalDate.of(2023, 5, 30), LocalDate.of(2024, 9, 1));
        assertEquals(41030, actual);
    }
}