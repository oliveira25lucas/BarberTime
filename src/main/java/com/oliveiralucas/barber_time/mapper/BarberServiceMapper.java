package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.BarberServiceDTO;
import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.data.dto.summary.BarberServiceSummaryDTO;
import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.model.Shop;
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

    @Named("barberServiceSummary")
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "durationMin", source = "durationMin"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "barberId", source = "barber.id"),
            @Mapping(target = "barberName", source = "barber.name")
    })
    BarberServiceSummaryDTO toSummary(BarberService entity);

    @IterableMapping(qualifiedByName = "barberServiceSummary")
    List<BarberServiceSummaryDTO> toSummary(List<BarberService> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "barber", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "durationMin", source = "durationMin"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    BarberService toEntity(BarberServiceDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(BarberServiceDTO dto, @MappingTarget BarberService entity);
}
