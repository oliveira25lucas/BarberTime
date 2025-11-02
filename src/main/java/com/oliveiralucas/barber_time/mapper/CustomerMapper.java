package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.CustomerDTO;
import com.oliveiralucas.barber_time.model.Customer;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO toDTO(Customer entity);
    List<CustomerDTO> toDTO(List<Customer> entities);

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CustomerDTO dto, @MappingTarget Customer entity);
}
