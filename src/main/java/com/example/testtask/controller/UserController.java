package com.example.testtask.controller;

import com.example.testtask.dto.UserDto;
import com.example.testtask.service.UserService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        URI location = builder.path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @PatchMapping("{id}/address")
    public ResponseEntity<?> updateUserAddress(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        userService.updateUserAddress(id, userDto);
        return ResponseEntity.ok("");
    }

    @PatchMapping("{id}/phone-number")
    public ResponseEntity<?> updateUserPhoneNumber(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        userService.updateUserPhoneNumber(id, userDto);
        return ResponseEntity.ok("");

    }

    @GetMapping("/search")
    public List<UserDto> getUsersByBirthDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return userService.getUsersByBirthDateRange(fromDate, toDate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
