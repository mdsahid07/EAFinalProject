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

    public User createCustomer(User user) {
        return userRepository.save(user);
    }

    public User getCustomerById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }

//    public User updateCustomer(Long id, User userDetails) {
//        User user = getCustomerById(id);
//        user.setUserName(userDetails.getUserName());
//        user.setPassword(userDetails.getPassword());
//        user.setRole(userDetails.getRole());
//        user.setEmail(userDetails.getEmail());
//        user.setPhoneNumber(userDetails.getPhoneNumber());
//        return userRepository.save(user);
//    }

    public void deleteCustomer(Long id) {
        userRepository.deleteById(id);
    }
}
