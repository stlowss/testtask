package com.example.testtask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AgeValidatorTest {
    private com.example.testtask.service.AgeValidator ageValidator;
    private final int age = 18;

    @BeforeEach
    void setUp() {
        ageValidator = new com.example.testtask.service.AgeValidator();
        ReflectionTestUtils.setField(ageValidator, "age", age);
    }

    @Test
    void whenBirthDateIsNull_ThenReturnFalse() {
        //WHEN
        boolean isValidAge = ageValidator.validateAgeByBirthDate(null);
        //THEN
        assertFalse(isValidAge);
    }

    @Test
    void whenBirthDateIsNotEqualToAllowedAge_ThenReturnFalse() {
        //GIVEN
        int notAllowedAge = age - 5;
        LocalDate birthDateOfNotAllowedAge = LocalDate.now().minusYears(notAllowedAge);
        //WHEN
        boolean isValidAge = ageValidator.validateAgeByBirthDate(birthDateOfNotAllowedAge);
        //THEN
        assertFalse(isValidAge);
    }

    @Test
    void whenBirthDateIsEqualToAllowedAge_ThenReturnTrue() {
        //GIVEN
        int allowedAge = age;
        LocalDate birthDateOfAllowedAge = LocalDate.now().minusYears(allowedAge);
        //WHEN
        boolean isValidAge = ageValidator.validateAgeByBirthDate(birthDateOfAllowedAge);
        //THEN
        assertTrue(isValidAge);
    }
}
