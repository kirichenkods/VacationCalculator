package ru.neoflex.vacationcalculator.service;

import ru.neoflex.vacationcalculator.dto.VacationRequestDTO;
import ru.neoflex.vacationcalculator.dto.VacationResponseDTO;
import ru.neoflex.vacationcalculator.mapper.VacationMapper;
import ru.neoflex.vacationcalculator.model.VacationData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final HolidaysService holidaysService;
    private final VacationMapper mapper;

    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;
    private static final int LAST_DAY_OFFSET = 1;
    private static final int COUNT_OF_DECIMAL_PLACES = 2;

    public VacationResponseDTO calcPayment(VacationRequestDTO request) {
        VacationData vacationData = mapper.toEntityFromRequest(request);
        vacationData.setEndDate(getEndDate(vacationData.getStartDate(), vacationData.getDuration()));
        int countHolidays = holidaysService.getCountHolidaysBetweenDates(
                vacationData.getStartDate(),
                vacationData.getEndDate());
        int countVacationDays = vacationData.getDuration() - countHolidays;
        double amountPayment = vacationData.getAverageSalary() / AVERAGE_DAYS_IN_MONTH * countVacationDays;
        BigDecimal paymentRounded = new BigDecimal(amountPayment);
        paymentRounded = paymentRounded.setScale(COUNT_OF_DECIMAL_PLACES, RoundingMode.HALF_UP);
        vacationData.setAmountPayment(paymentRounded.doubleValue());

        return mapper.toResponseDTO(vacationData);
    }

    private LocalDate getEndDate(LocalDate startDate, Integer duration) {
        return startDate.plusDays(duration - LAST_DAY_OFFSET);
    }
}