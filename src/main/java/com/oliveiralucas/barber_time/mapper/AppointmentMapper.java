package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.model.Appointment;
import com.oliveiralucas.barber_time.model.Shop;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ShopMapper.class,
                CustomerMapper.class,
                BarberMapper.class,
                BarberServiceMapper.class,
                ProductMapper.class
        }
)
public interface AppointmentMapper {

    AppointmentDTO toDTO(Appointment entity);
    List<AppointmentDTO> toDTO(List<Appointment> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "shop", ignore = true),
            @Mapping(target = "customer", ignore = true),
            @Mapping(target = "barber", ignore = true),
            @Mapping(target = "barberService", ignore = true),
            @Mapping(target = "product", ignore = true),
            @Mapping(target = "startAt", source = "startAt"),
            @Mapping(target = "endAt", source = "endAt"),
            @Mapping(target = "appointmentStatus", source = "appointmentStatus"),
            @Mapping(target = "paymentStatus", source = "paymentStatus"),
            @Mapping(target = "notes", source = "notes"),
            @Mapping(target = "totalPrice", source = "totalPrice"),
            @Mapping(target = "currency", source = "currency"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    Appointment toEntity(AppointmentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AppointmentDTO dto, @MappingTarget Appointment entity);
}
