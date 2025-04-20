package ru.neoflex.vacationcalculator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VacationRequestDTO {
    @Positive(message = "Средняя зарплата должна быть положительной")
    private double averageSalary;
    @NotNull(message = "Начальная дата отпуска обязательна")
    private LocalDate startDate;
    @Min(value = 1, message = "Продолжительность отпуска должна быть минимум 1 день")
    private Integer duration;
}
