package com.oliveiralucas.barber_time.data.dto;

import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ShopDTO {

    private Long id;
    private String name;
    private String cnpj;
    private String phone;
    private String email;
    private String address;
    private BigDecimal ratingAverage;
    private Integer ratingCount;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}