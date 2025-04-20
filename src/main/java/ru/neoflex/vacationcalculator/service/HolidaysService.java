package ru.neoflex.vacationcalculator.service;

import ru.neoflex.vacationcalculator.repository.HolidaysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Сервис для работы с информацией о государственных праздниках.
 */
@Service
@RequiredArgsConstructor
public class HolidaysService {
    private final HolidaysRepository repository;

    /**
     * Подсчитывает количество праздничных дней между двумя датами.
     * @param dateStart начальная дата диапазона
     * @param dateEnd конечная дата диапазона
     * @return количество праздничных дней между указанными датами включительно
     */
    public int getCountHolidaysBetweenDates(LocalDate dateStart, LocalDate dateEnd) {
        int dayStart = dateStart.getDayOfMonth();
        int monthStart = dateStart.getMonthValue();
        int dayEnd = dateEnd.getDayOfMonth();
        int monthEnd = dateEnd.getMonthValue();

        return repository.countByDayBetweenAndMonthBetween(dayStart, dayEnd, monthStart, monthEnd);
    }
}
