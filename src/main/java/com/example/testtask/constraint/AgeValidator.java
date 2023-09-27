package com.example.testtask.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    @Value("${user.min-age}")
    private int age;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        if (birthDate == null || birthDate.isAfter(now)) {
            return false;
        }
        int years = Period.between(birthDate, now).getYears();
        formatMessage(context);
        return years >= age;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String messageTemplate = context.getDefaultConstraintMessageTemplate();
        context.disableDefaultConstraintViolation();
        String formattedTemplate = messageTemplate.formatted(this.age);
        context.buildConstraintViolationWithTemplate(formattedTemplate).addConstraintViolation();
    }
}
