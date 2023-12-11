package com.chielokacodes.ec.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.entity.Admin;
import com.chielokacodes.ec.entity.User;
import com.chielokacodes.ec.repository.AdminRepository;
import com.chielokacodes.ec.repository.UserRepository;
import com.chielokacodes.ec.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }



    @Override
    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public boolean verifyPassword(PasswordDto passwordDto) {
        return BCrypt.verifyer().verify(
                passwordDto.getPassword().toCharArray(),
                passwordDto.getHashPassword().toCharArray()).verified;
    }
}
