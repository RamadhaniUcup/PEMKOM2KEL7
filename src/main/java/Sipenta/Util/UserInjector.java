package Sipenta.Util;

import Sipenta.Services.AuthService;

public class UserInjector {

    public static void main(String[] args) {

        AuthService userService = new AuthService();

        /*
         * Membuat user admin awal.
         * Password "123" akan otomatis diubah menjadi hash SHA-256
         * oleh method registerUser() di AuthService.
         */
        userService.registerUser("Ketua Tim Sipenta", "admin", "123");

        System.out.println("User admin berhasil diproses.");
        System.out.println("Username : admin");
        System.out.println("Password : 123");
    }
}