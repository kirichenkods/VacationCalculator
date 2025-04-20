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

@RestController
@RequiredArgsConstructor
public class VacationController {
    private final VacationService service;

    @GetMapping("/calculacte")
    public ResponseEntity<VacationResponseDTO> calculate(
            @Valid @RequestBody VacationRequestDTO request) {
        VacationResponseDTO response = service.calcPayment(request);
        return ResponseEntity.ok(response);
    }
}
