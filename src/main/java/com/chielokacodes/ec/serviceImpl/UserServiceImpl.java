package com.chielokacodes.ec.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.entity.User;
import com.chielokacodes.ec.repository.UserRepository;
import com.chielokacodes.ec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }



    @Override
    public User findUserByEmail(String email) {
       return userRepository.findByEmail(email);
    }



    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new NullPointerException("User not found!"));
    }



    @Override
    public boolean verifyPassword(PasswordDto passwordDto) {
        return BCrypt.verifyer().verify(
                passwordDto.getPassword().toCharArray(),
                passwordDto.getHashPassword().toCharArray()).verified;
    }


}
