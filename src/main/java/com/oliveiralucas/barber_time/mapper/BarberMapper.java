package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.model.Shop;
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

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "shop", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "phone", source = "phone"),
            @Mapping(target = "birthday", source = "birthday"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "cpf", source = "cpf"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "ratingAverage", source = "ratingAverage"),
            @Mapping(target = "ratingCount", source = "ratingCount"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "gender", source = "gender"),
            @Mapping(target = "startShift", source = "startShift"),
            @Mapping(target = "endShift", source = "endShift"),
            @Mapping(target = "startLunch", source = "startLunch"),
            @Mapping(target = "endLunch", source = "endLunch"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    Barber toEntity(BarberDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(BarberDTO dto, @MappingTarget Barber entity);
}
