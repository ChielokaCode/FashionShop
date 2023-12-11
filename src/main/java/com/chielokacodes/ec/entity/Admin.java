package com.chielokacodes.ec.entity;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.chielokacodes.ec.dto.AdminDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;
    private String password;
    private String image;

    public Admin(AdminDto adminDto){
        this.username = adminDto.getUsername();
        this.email = adminDto.getEmail();
        this.password = BCrypt.withDefaults().hashToString(12, adminDto.getPassword().toCharArray());
    }
}
