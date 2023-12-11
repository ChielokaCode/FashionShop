package com.chielokacodes.ec.entity;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.chielokacodes.ec.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    private String email;
    private String password;
    private String image;
    private BigDecimal balance;

    public User(UserDto userDto){
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = BCrypt.withDefaults().hashToString(12, userDto.getPassword().toCharArray());
        this.balance = new BigDecimal(25000000);
    }
}
