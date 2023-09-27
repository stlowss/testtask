package com.example.testtask.converter;

import com.example.testtask.dto.UserDto;
import com.example.testtask.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends com.example.testtask.converter.Converter<User, UserDto> {
    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthDate(dto.getBirthDate());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());

        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setBirthDate(entity.getBirthDate());
        userDto.setAddress(entity.getAddress());
        userDto.setPhoneNumber(entity.getPhoneNumber());

        return userDto;
    }
}
