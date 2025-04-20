package ru.neoflex.vacationcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "holidays")
public class Holidays {
    @Id
    @SequenceGenerator(name = "pk_holiday_seq", sequenceName = "holidays_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_holiday_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "month", nullable = false)
    private int month;
}