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

/**
 * Сервис для расчёта отпускных выплат сотрудников.
 */
@Service
@RequiredArgsConstructor
public class VacationService {
    private final HolidaysService holidaysService;
    private final VacationMapper mapper;

    //  Среднее количество календарных дней в месяце, используемое для расчётов
    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;
    // Смещение последнего дня отпуска относительно начальной даты
    private static final int LAST_DAY_OFFSET = 1;
    // Количество знаков после запятой для округления суммы оплаты.
    private static final int COUNT_OF_DECIMAL_PLACES = 2;


    /**
     * Рассчитывает оплату отпуска на основе предоставленной информации.
     * @param request {@link VacationRequestDTO}, Данные запроса, содержащие среднюю зарплату,
     *                начальную дату и продолжительность отпуска.
     * @return объект {@link VacationResponseDTO}, содержащий расчёт отпускных выплат.
     */
    public VacationResponseDTO calcPayment(VacationRequestDTO request) {
        VacationData vacationData = mapper.toEntityFromRequest(request);
        // Получение последней даты отпуска
        vacationData.setEndDate(getEndDate(vacationData.getStartDate(), vacationData.getDuration()));
        // Количество праздничных дней за время отпуска
        int countHolidays = holidaysService.getCountHolidaysBetweenDates(
                vacationData.getStartDate(),
                vacationData.getEndDate());
        // Итоговое количество дней отпуска для расчета
        int countVacationDays = vacationData.getDuration() - countHolidays;
        // Расчет выплаты
        double amountPayment = vacationData.getAverageSalary() / AVERAGE_DAYS_IN_MONTH * countVacationDays;
        // Округление результата
        BigDecimal paymentRounded = new BigDecimal(amountPayment);
        paymentRounded = paymentRounded.setScale(COUNT_OF_DECIMAL_PLACES, RoundingMode.HALF_UP);
        vacationData.setAmountPayment(paymentRounded.doubleValue());

        return mapper.toResponseDTO(vacationData);
    }

    /**
     * Определяет последнюю дату отпуска на основе начальной даты и заданной продолжительности.
     * @param startDate Начальная дата отпуска.
     * @param duration Длительность отпуска в днях.
     * @return Последняя дата отпуска включительно.
     */
    private LocalDate getEndDate(LocalDate startDate, Integer duration) {
        return startDate.plusDays(duration - LAST_DAY_OFFSET);
    }
}