package com.chielokacodes.ec.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String productIds;
    private Long userId;
}
