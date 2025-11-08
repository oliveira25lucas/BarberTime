package com.oliveiralucas.barber_time.data.dto.summary;

import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShopSummaryDTO {
    Long id;
    String name;
    BigDecimal ratingAverage;
    Integer ratingCount;
    StatusEnum status;
}
