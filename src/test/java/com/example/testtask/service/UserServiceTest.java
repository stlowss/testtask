package com.example.testtask.service;

import com.example.testtask.converter.UserConverter;
import com.example.testtask.dto.UserDto;
import com.example.testtask.exception.AgeNotValidException;
import com.example.testtask.exception.InvalidDateRangeException;
import com.example.testtask.model.User;
import com.example.testtask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @Test
    void whenUpdateUserAddress_ThenReturnedEqualUserDto() {
        //GIVEN
        when(userRepository.findById(any())).thenAnswer(inv -> {
            User user = new User();
            user.setId(inv.getArgument(0));
            return Optional.of(user);
        });
        UserDto userDto = new UserDto();
        userDto.setAddress("test");

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        //WHEN
        userService.updateUserAddress(1L, userDto);

        //THEN
        verify(userRepository).save(argumentCaptor.capture());
        User userActual = argumentCaptor.getValue();
        assertEquals(userDto.getAddress(), userActual.getAddress());
    }


    @Test
    void whenUpdateUserPhoneNumber_ThenReturnedEqualUserDto() {
        //GIVEN
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        userDto.setPhoneNumber("test phone");

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        //WHEN
        userService.updateUserPhoneNumber(1L, userDto);

        //THEN
        verify(userRepository).save(argumentCaptor.capture());
        User userActual = argumentCaptor.getValue();
        assertEquals(userDto.getPhoneNumber(), userActual.getPhoneNumber());
    }

    @Test
    void whenGetUsersWithNotValidRangeOfBirthDate_ThenThrowsException() {
        //GIVEN
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().minusYears(10);

        //WHEN
        Executable executable = () -> userService.getUsersByBirthDateRange(from, to);

        //THEN
        assertThrows(InvalidDateRangeException.class, executable);
    }

    @Test
    void whenCreateUser_ThenSaveUser() {
        //GIVEN
        UserDto userDto = createUserDto();

        //WHEN
        userService.createUser(userDto);

        //THEN
        verify(userRepository, times(1)).save(any());
        verify(userConverter, times(1)).toDto(any());
    }

    @Test
    void whenUpdateExistingUser_ThenUpdateUser() {
        //GIVEN
        UserDto userDto = createUserDto();
        long id = 1L;
        when(userRepository.findById(id)).thenAnswer(inv -> {
            User user = new User();
            user.setId(inv.getArgument(0));
            return Optional.of(user);
        });
        when(userConverter.toEntity(userDto)).thenReturn(new User());

        //WHEN
        userService.updateUser(id, userDto);

        //THEN
        verify(userRepository, times(1)).save(any());
        verify(userConverter, times(1)).toDto(any());
    }

    @Test
    void whenGetUsersWithValidRangeOfBirthDate_ThenGetUserList() {
        //GIVEN
        LocalDate from = LocalDate.now().minusYears(10);
        LocalDate to = LocalDate.now();

        //WHEN
        userService.getUsersByBirthDateRange(from, to);

        //THEN
        verify(userRepository, times(1)).findAllByBirthDateBetween(from, to);
        verify(userConverter, times(1)).toDtoList(anyList());
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("some@email.com");
        userDto.setBirthDate(LocalDate.now().minusYears(18));
        userDto.setFirstName("test name");
        userDto.setLastName("test last name");
        userDto.setAddress("Test street");
        userDto.setPhoneNumber("+380000000");
        return userDto;
    }
}
