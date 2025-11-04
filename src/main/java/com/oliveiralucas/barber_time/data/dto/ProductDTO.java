package com.oliveiralucas.barber_time.data.dto;

import com.oliveiralucas.barber_time.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {


    private Long id;
    private ShopDTO shop;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
