package com.chielokacodes.ec.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String productIds;

//    private String productIds;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Order order;
}