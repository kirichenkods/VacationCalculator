package ru.neoflex.vacationcalculator.service;

import ru.neoflex.vacationcalculator.repository.HolidaysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HolidaysService {
    private final HolidaysRepository repository;
    public int getCountHolidaysBetweenDates(LocalDate dateStart, LocalDate dateEnd) {
        int dayStart = dateStart.getDayOfMonth();
        int monthStart = dateStart.getMonthValue();
        int dayEnd = dateEnd.getDayOfMonth();
        int monthEnd = dateEnd.getMonthValue();

        return repository.countByDayBetweenAndMonthBetween(dayStart, dayEnd, monthStart, monthEnd);
    }
}
