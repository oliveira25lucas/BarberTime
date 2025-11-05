package com.oliveiralucas.barber_time.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_shop"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_customer"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_barber"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull
    private Barber barber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_service_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointment_bs"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BarberService barberService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_appointment_product"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @Column(name = "start_at", nullable = false)
    @NotNull
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    @NotNull
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false, length = 20)
    private AppointmentStatusEnum appointmentStatus = AppointmentStatusEnum.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatusEnum paymentStatus = PaymentStatusEnum.OPEN;

    @Column(name = "notes", length = 1000)
    @Size(max = 1000)
    private String notes;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "BRL";

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
