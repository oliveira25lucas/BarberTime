package com.oliveiralucas.barber_time.data.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ShopDTO {

    private Long id;
    private String name;
    private String cnpj;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    private String email;
    private String address;
    private BigDecimal ratingAverage;
    private Integer ratingCount;
    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"open_time", "opentime"})
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"close_time", "closetime"})
    private LocalTime closeTime;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}