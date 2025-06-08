package com.example.vill0990_a1.functions;

public class LoginValidator {

    public static boolean validEmail(String email){
        return !email.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    public static boolean validPassword(String password){
        return !password.isEmpty();
    }

}
