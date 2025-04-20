package ru.neoflex.vacationcalculator.mapper;

import ru.neoflex.vacationcalculator.dto.VacationRequestDTO;
import ru.neoflex.vacationcalculator.dto.VacationResponseDTO;
import ru.neoflex.vacationcalculator.model.VacationData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VacationMapper {
    private final ModelMapper mapper;

    public VacationData toEntityFromRequest(VacationRequestDTO requestDTO) {
        return Objects.isNull(requestDTO) ?
                null :
                mapper.map(requestDTO, VacationData.class);
    }

    public VacationResponseDTO toResponseDTO(VacationData vacationData) {
        return Objects.isNull(vacationData) ?
                null :
                mapper.map(vacationData, VacationResponseDTO.class);
    }
}
