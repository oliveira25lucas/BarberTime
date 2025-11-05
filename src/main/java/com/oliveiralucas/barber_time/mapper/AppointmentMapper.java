package com.oliveiralucas.barber_time.mapper;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.model.Appointment;
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

    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AppointmentDTO dto, @MappingTarget Appointment entity);
}
