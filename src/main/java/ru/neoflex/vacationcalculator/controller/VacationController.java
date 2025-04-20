package ru.neoflex.vacationcalculator.controller;

import ru.neoflex.vacationcalculator.dto.VacationRequestDTO;
import ru.neoflex.vacationcalculator.dto.VacationResponseDTO;
import ru.neoflex.vacationcalculator.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Контроллер для обработки запросов на расчёт отпускных выплат.
 */
@RestController
@RequiredArgsConstructor
public class VacationController {
    private final VacationService service;

    /**
     * Метод обрабатывает запросы GET по адресу '/calculate', принимает запрос на расчёт отпускных и
     * возвращает сформированные отпускные выплаты.
     * @param request {@link VacationRequestDTO} объект запроса с необходимой информацией для расчёта
     * @return объект {@link VacationResponseDTO}, содержащий сумму отпускных выплат
     */
    @GetMapping("/calculacte")
    public ResponseEntity<VacationResponseDTO> calculate(
            @Valid @RequestBody VacationRequestDTO request) {
        VacationResponseDTO response = service.calcPayment(request);
        return ResponseEntity.ok(response);
    }
}
