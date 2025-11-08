package com.oliveiralucas.barber_time.data.dto;

import com.oliveiralucas.barber_time.data.dto.summary.*;
import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {

    private Long id;
    private ShopSummaryDTO shop;
    private CustomerSummaryDTO customer;
    private BarberSummaryDTO barber;
    private BarberServiceSummaryDTO barberService;
    private ProductSummaryDTO product;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private AppointmentStatusEnum appointmentStatus;
    private PaymentStatusEnum paymentStatus;
    private String notes;
    private BigDecimal totalPrice;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
