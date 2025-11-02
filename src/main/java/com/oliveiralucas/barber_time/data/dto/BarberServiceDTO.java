package com.oliveiralucas.barber_time.data.dto;

import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BarberServiceDTO {

    private Long id;
    private BarberDTO barber;
    private String name;
    private String description;
    private Integer durationMin;
    private BigDecimal price;
    private StatusEnum status;

}
