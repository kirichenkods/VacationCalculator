package ru.neoflex.vacationcalculator.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class VacationData {
    private double averageSalary;
    private int vacationDays;
    private double amountPayment;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
}

