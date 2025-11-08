package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.CustomerDTO;
import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.data.dto.summary.CustomerSummaryDTO;
import com.oliveiralucas.barber_time.model.Customer;
import com.oliveiralucas.barber_time.model.Shop;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO toDTO(Customer entity);
    List<CustomerDTO> toDTO(List<Customer> entities);

    @Named("customerSummary")
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "ratingAverage", source = "ratingAverage"),
            @Mapping(target = "ratingCount", source = "ratingCount"),
            @Mapping(target = "status", source = "status")
    })
    CustomerSummaryDTO toSummary(Customer entity);

    @IterableMapping(qualifiedByName = "customerSummary")
    List<CustomerSummaryDTO> toSummary(List<Customer> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "phone", source = "phone"),
            @Mapping(target = "birthday", source = "birthday"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "cpf", source = "cpf"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "ratingAverage", source = "ratingAverage"),
            @Mapping(target = "ratingCount", source = "ratingCount"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "gender", source = "gender"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    Customer toEntity(CustomerDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CustomerDTO dto, @MappingTarget Customer entity);
}
