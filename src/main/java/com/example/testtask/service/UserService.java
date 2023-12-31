package com.example.testtask.service;

import com.example.testtask.converter.UserConverter;
import com.example.testtask.dto.UserDto;
import com.example.testtask.exception.AgeNotValidException;
import com.example.testtask.exception.InvalidDateRangeException;
import com.example.testtask.model.User;
import com.example.testtask.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;


    public UserService(UserRepository userRepository,
                       UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserDto createUser(UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        user = userRepository.save(user);

        return userConverter.toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUser(id);
        User updatedUser = userConverter.toEntity(userDto);
        updatedUser.setId(user.getId());
        userRepository.save(updatedUser);

        return userConverter.toDto(updatedUser);
    }

    public List<UserDto> getUsersByBirthDateRange(LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        List<User> usersByPeriodOfBirthDate = userRepository.findAllByBirthDateBetween(from, to);
        return userConverter.toDtoList(usersByPeriodOfBirthDate);
    }

    public UserDto updateUserAddress(Long id, UserDto userDto) {
        User user = getUser(id);
        user.setAddress(userDto.getAddress());
        user = userRepository.save(user);

        return userConverter.toDto(user);
    }

    public UserDto updateUserPhoneNumber(Long id, UserDto userDto) {
        User user = getUser(id);
        user.setPhoneNumber(userDto.getPhoneNumber());
        user = userRepository.save(user);

        return userConverter.toDto(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User with id %s not found".formatted(id));
        }
        userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id %s not found".formatted(id)));
    }

    private void validateDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new InvalidDateRangeException("From date must be not less than To date");
        }
    }
}
