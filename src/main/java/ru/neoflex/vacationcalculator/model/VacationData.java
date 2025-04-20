package ru.neoflex.vacationcalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VacationData {
    private double averageSalary;
    private int vacationDays;
    private double amountPayment;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
}

