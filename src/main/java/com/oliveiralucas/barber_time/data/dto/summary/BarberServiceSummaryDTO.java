package com.oliveiralucas.barber_time.data.dto.summary;

import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BarberServiceSummaryDTO {
    Long id;
    String name;
    Integer durationMin;
    BigDecimal price;
    StatusEnum status;
    Long barberId;
    String barberName;
}
