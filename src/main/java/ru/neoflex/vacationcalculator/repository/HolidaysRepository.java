package ru.neoflex.vacationcalculator.repository;

import ru.neoflex.vacationcalculator.model.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
    int countByDayBetweenAndMonthBetween(int dayStart, int dayEnd, int monthStart, int monthEnd);
}
