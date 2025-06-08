package com.example.vill0990_a1.functions;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginValidatorTest {

    @Test
    public void validEmail_validEmail_returnsTrue() {
        assertTrue(LoginValidator.validEmail("user@example.com"));
    }

    @Test
    public void validEmail_emptyEmail_returnsFalse() {
        assertFalse(LoginValidator.validEmail(""));
    }

    @Test
    public void validEmail_invalidEmail_returnsFalse() {
        assertFalse(LoginValidator.validEmail("invalid-email"));
        assertFalse(LoginValidator.validEmail("user@.com"));
        assertFalse(LoginValidator.validEmail("user@domain"));
    }

    @Test
    public void validPassword_valid_returnsTrue() {
        assertTrue(LoginValidator.validPassword("password123"));
    }

    @Test
    public void validPassword_empty_returnsFalse() {
        assertFalse(LoginValidator.validPassword(""));
    }
}