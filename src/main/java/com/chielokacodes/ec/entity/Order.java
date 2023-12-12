package com.chielokacodes.ec.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "`order`")  // Use backticks for MySQL
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_ids")
    private String productIds;

    @OneToMany(mappedBy = "id")
    @Column(name = "product_ids")
    private List<Product> productList;


    @Column(name = "total_price", precision = 38, scale = 2)
    private BigDecimal totalPrice;

//    @OneToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
}
