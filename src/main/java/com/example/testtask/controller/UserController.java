package com.example.testtask.controller;

import com.example.testtask.dto.UserDto;
import com.example.testtask.service.UserService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PatchMapping("{id}/address")
    public UserDto updateUserAddress(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.updateUserAddress(id, userDto);
    }

    @PatchMapping("{id}/phone-number")
    public UserDto updateUserPhoneNumber(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.updateUserPhoneNumber(id, userDto);
    }

    @GetMapping("/search")
    public List<UserDto> getUsersByBirthDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return userService.getUsersByBirthDateRange(fromDate, toDate);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
