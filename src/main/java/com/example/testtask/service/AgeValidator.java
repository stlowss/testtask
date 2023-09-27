package com.example.testtask.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AgeValidator {
    @Value("${user.min-age}")
    private int age;

    public boolean validateAgeByBirthDate(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        if(birthDate == null || birthDate.isAfter(now)){
            return false;
        }
        int years = Period.between(birthDate, now).getYears();
        return years >= age;
    }

    public int getAge() {
        return age;
    }
}
