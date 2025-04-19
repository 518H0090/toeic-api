package com.toeic.auth.adapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.toeic.auth.application.dto.AddUserDto;
import com.toeic.auth.application.dto.UpdateUserDto;
import com.toeic.auth.application.service.IUserService;
import com.toeic.auth.domain.model.Users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register") 
    public ResponseEntity<Users> registerUser(@RequestBody @Valid AddUserDto request) {
        Users user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<Users> updateUser(@RequestBody @Valid UpdateUserDto request) {
        Users user = userService.updateUser(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}") 
    public ResponseEntity<Users> getUserByUsername(@PathVariable("username") String username) {
        Optional<Users> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
