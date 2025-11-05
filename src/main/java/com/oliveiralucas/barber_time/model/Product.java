package com.oliveiralucas.barber_time.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name= "product",
        uniqueConstraints=@UniqueConstraint(name="uk_product_name", columnNames={"shop_id","name"}))
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_shop"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Shop shop;

    @NotBlank
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
