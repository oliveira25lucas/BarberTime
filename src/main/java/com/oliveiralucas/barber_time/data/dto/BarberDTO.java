package com.oliveiralucas.barber_time.data.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oliveiralucas.barber_time.enums.GenderEnum;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class BarberDTO {

    private Long id;
    private ShopDTO shop;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String description;
    private String phone;
    private LocalDate birthday;
    private String address;
    private String cpf;
    private BigDecimal ratingAverage;
    private Integer ratingCount;
    private StatusEnum status;
    private GenderEnum gender;
    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"startshift", "start_shift"})
    private LocalTime startShift;

    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"endshift", "end_shift"})
    private LocalTime endShift;

    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"startlunch", "start_lunch"})
    private LocalTime startLunch;

    @JsonFormat(pattern = "HH:mm")
    @JsonAlias({"endlunch", "end_lunch"})
    private LocalTime endLunch;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
