package com.chielokacodes.ec.service;

import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.entity.Admin;
import com.chielokacodes.ec.entity.User;

public interface AdminService {
    Admin saveAdmin(Admin admin);

    Admin findAdminByEmail(String email);

    boolean verifyPassword(PasswordDto passwordDto);
}
