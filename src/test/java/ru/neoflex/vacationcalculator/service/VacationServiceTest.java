package ru.neoflex.vacationcalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import ru.neoflex.vacationcalculator.dto.VacationRequestDTO;
import ru.neoflex.vacationcalculator.dto.VacationResponseDTO;
import ru.neoflex.vacationcalculator.mapper.VacationMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

class VacationServiceTest {
    private VacationService service;
    private HolidaysService mockHolidaysService;
    private VacationMapper mapper;

    @BeforeEach
    void setUp() {
        this.mockHolidaysService = Mockito.mock(HolidaysService.class);
        this.mapper = new VacationMapper(new ModelMapper());
        this.service = new VacationService(mockHolidaysService, mapper);
    }

    // Проверка стандартного случая без праздников
    @Test
    void givenNoHolidays_whenCalculatePayment_thenReturnsExpectedAmount() {
        LocalDate startDate = LocalDate.of(2025, 3, 31);
        int duration = 14;

        // праздничных дней во время отпуска нет
        when(mockHolidaysService
                .getCountHolidaysBetweenDates(startDate, startDate.plusDays(duration - 1)))
                .thenReturn(0);

        VacationRequestDTO request = new VacationRequestDTO();
        request.setAverageSalary(50_000);
        request.setStartDate(startDate);
        request.setDuration(duration);

        VacationResponseDTO response = service.calcPayment(request);

        assertEquals(23890.78, response.getAmountPayment());
    }

    // Проверка ситуации, когда дни отпуска совпадают с праздниками
    @Test
    void givenAllDaysAreHolidays_whenCalculatePayment_thenReturnsZeroAmount() {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        int duration = 7;

        // все дни отпуска - праздничные
        when(mockHolidaysService
                .getCountHolidaysBetweenDates(startDate, startDate.plusDays(duration - 1)))
                .thenReturn(7);

        VacationRequestDTO request = new VacationRequestDTO();
        request.setAverageSalary(50_000);
        request.setStartDate(startDate);
        request.setDuration(duration);

        VacationResponseDTO response = service.calcPayment(request);

        assertEquals(0.0, response.getAmountPayment());
    }

    // Один праздник внутри отпуска
    @Test
    void givenOneHolidayInVacationPeriod_whenCalculatePayment_thenReturnsReducedAmount() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        int duration = 14;

        // один праздничный день за время отпуска
        when(mockHolidaysService
                .getCountHolidaysBetweenDates(startDate, startDate.plusDays(duration - 1)))
                .thenReturn(1);

        VacationRequestDTO request = new VacationRequestDTO();
        request.setAverageSalary(30_000);
        request.setStartDate(startDate);
        request.setDuration(duration);

        VacationResponseDTO response = service.calcPayment(request);

        assertEquals(13310.58, response.getAmountPayment());
    }

    // Несколько праздников внутри отпуска
    @Test
    void givenMultipleHolidaysInVacationPeriod_whenCalculatePayment_thenReturnsReducedAmount() {
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        int duration = 10;

        when(mockHolidaysService
                .getCountHolidaysBetweenDates(startDate, startDate.plusDays(duration - 1)))
                .thenReturn(2);

        VacationRequestDTO request = new VacationRequestDTO();
        request.setAverageSalary(150_000);
        request.setStartDate(startDate);
        request.setDuration(duration);

        VacationResponseDTO response = service.calcPayment(request);

        assertEquals(40955.63, response.getAmountPayment());
    }
}