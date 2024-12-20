package edu.miu.cs.cs544.sahid.hotelbookingsystem.controller;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.User;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class OldCustomerController {

    @Autowired
    private final UserService userService;
    public OldCustomerController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createCustomer(@RequestBody User user) {
        return ResponseEntity.ok(userService.createCustomer(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllCustomers() {
        return ResponseEntity.ok(userService.getAllCustomers());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateCustomer(@PathVariable Long id, @RequestBody User userDetails) {
//        return ResponseEntity.ok(userService.updateCustomer(id, userDetails));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        userService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
