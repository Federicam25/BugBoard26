package com.bugboard26.utils;

public class GenerateHash {
    public static void main(String[] args) {
        String password = "password";
        String hash = PasswordUtil.hashPassword(password);
        System.out.println("Hash generato: " + hash);
    }
}