package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ShopMapper.class
)
public interface ProductMapper {

    ProductDTO toDTO(Product entity);
    List<ProductDTO> toDTO(List<Product> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "shop", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "quantity", source = "quantity"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "status", source = "status")
    })
    Product toEntity(ProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ProductDTO dto, @MappingTarget Product entity);
}
