package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.User;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRespository userRepository;
    public UserService(UserRespository userRepository) {
        this.userRepository = userRepository;
    }

}
