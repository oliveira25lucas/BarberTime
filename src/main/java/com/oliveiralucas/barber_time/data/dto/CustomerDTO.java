package com.oliveiralucas.barber_time.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oliveiralucas.barber_time.enums.GenderEnum;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    private LocalDate birthday;
    private String address;
    private String cpf;
    private BigDecimal ratingAverage;
    private Integer ratingCount;
    private StatusEnum status;
    private GenderEnum gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
