package com.example.testtask.converter;

import com.example.testtask.dto.UserDto;
import com.example.testtask.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {
    private final UserConverter userConverter = new UserConverter();

    @Test
    void whenConvertToDto_ThenReturnEqualDto(){
        //GIVEN
        User user = createUser();
        //WHEN
        UserDto userDto = userConverter.toDto(user);
        //THEN
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getBirthDate(), userDto.getBirthDate());
        assertEquals(user.getAddress(), userDto.getAddress());
        assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
    }

    @Test
    void whenConvertToEntity_ThenReturnEqualEntity(){
        //GIVEN
        UserDto userDto = createUserDto();
        //WHEN
        User user = userConverter.toEntity(userDto);
        //THEN
        assertNotEquals(userDto.getId(), user.getId());
        assertNull(user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getBirthDate(), user.getBirthDate());
        assertEquals(userDto.getAddress(), user.getAddress());
        assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setEmail("someDto@email.com");
        userDto.setBirthDate(LocalDate.now().minusYears(18));
        userDto.setFirstName("test dto name");
        userDto.setLastName("test dto last name");
        userDto.setAddress("Test dto street");
        userDto.setPhoneNumber("+380000001");
        return userDto;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("some@email.com");
        user.setBirthDate(LocalDate.now().minusYears(20));
        user.setFirstName("test name");
        user.setLastName("test last name");
        user.setAddress("Test street");
        user.setPhoneNumber("+380000000");
        return user;
    }
}
