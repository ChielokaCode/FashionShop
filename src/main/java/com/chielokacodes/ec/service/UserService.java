package com.chielokacodes.ec.service;

import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.dto.UserDto;
import com.chielokacodes.ec.entity.User;

public interface UserService {

    User saveUser(User user);

//    User findUserById(Long id);

    User findUserByEmail(String email);

    User findUserById(Long id);

    boolean verifyPassword(PasswordDto passwordDto);
}
