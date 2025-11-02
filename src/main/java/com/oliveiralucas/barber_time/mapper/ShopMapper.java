package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.model.Shop;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ShopMapper {

    ShopDTO toDTO(Shop entity);
    List<ShopDTO> toDTO(List<Shop> entities);

    @Mapping(target = "id", ignore = true)
    Shop toEntity(ShopDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ShopDTO dto, @MappingTarget Shop entity);
}
