package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.data.dto.summary.ShopSummaryDTO;
import com.oliveiralucas.barber_time.model.Product;
import com.oliveiralucas.barber_time.model.Shop;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ShopMapper {

    ShopDTO toDTO(Shop entity);
    List<ShopDTO> toDTO(List<Shop> entities);

    @Named("shopSummary")
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "ratingAverage", source = "ratingAverage"),
            @Mapping(target = "ratingCount", source = "ratingCount"),
            @Mapping(target = "status", source = "status")
    })
    ShopSummaryDTO toSummary(Shop entity);

    @IterableMapping(qualifiedByName = "shopSummary")
    List<ShopSummaryDTO> toSummary(List<Shop> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "cnpj", source = "cnpj"),
            @Mapping(target = "phone", source = "phone"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "ratingAverage", source = "ratingAverage"),
            @Mapping(target = "ratingCount", source = "ratingCount"),
            @Mapping(target = "openTime", source = "openTime"),
            @Mapping(target = "closeTime", source = "closeTime"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    Shop toEntity(ShopDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ShopDTO dto, @MappingTarget Shop entity);
}
