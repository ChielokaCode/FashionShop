package com.chielokacodes.ec.repository;

import com.chielokacodes.ec.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

}
