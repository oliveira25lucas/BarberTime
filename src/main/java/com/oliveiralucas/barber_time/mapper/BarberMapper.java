package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.model.Barber;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ShopMapper.class
)
public interface BarberMapper {

    BarberDTO toDTO(Barber entity);
    List<BarberDTO> toDTO(List<Barber> entities);

    @Mapping(target = "id", ignore = true)
    Barber toEntity(BarberDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(BarberDTO dto, @MappingTarget Barber entity);
}
