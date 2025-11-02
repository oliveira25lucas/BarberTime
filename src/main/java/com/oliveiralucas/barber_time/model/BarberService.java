package com.oliveiralucas.barber_time.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "barber_service",
        uniqueConstraints=@UniqueConstraint(name="uk_barber_name", columnNames={"barber_id","name"}))
@Getter
@Setter
public class BarberService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bs_barber"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Barber barber;

    @NotBlank
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", length = 240)
    private String description;

    @NotNull
    @Min(1)
    @Max(480)
    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.ACTIVE;

}
