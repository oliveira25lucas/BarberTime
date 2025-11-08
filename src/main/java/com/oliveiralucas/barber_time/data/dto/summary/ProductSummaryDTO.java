package com.oliveiralucas.barber_time.data.dto.summary;

import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSummaryDTO {
    Long id;
    String name;
    BigDecimal price;
    Integer quantity;
    StatusEnum status;
    Long shopId;
    String shopName;
}
