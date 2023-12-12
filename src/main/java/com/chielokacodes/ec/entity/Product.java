package com.chielokacodes.ec.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String category;
    private Long quantity;
    private String image;
    private BigDecimal price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
