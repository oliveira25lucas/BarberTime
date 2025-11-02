package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.BarberServiceDTO;
import com.oliveiralucas.barber_time.model.BarberService;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = BarberMapper.class
)
public interface BarberServiceMapper {

    BarberServiceDTO toDTO(BarberService entity);
    List<BarberServiceDTO> toDTO(List<BarberService> entities);

    @Mapping(target = "id", ignore = true)
    BarberService toEntity(BarberServiceDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(BarberServiceDTO dto, @MappingTarget BarberService entity);
}
