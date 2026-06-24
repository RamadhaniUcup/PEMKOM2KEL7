/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.utils;

import Sipenta.services.AuthService;

/**
 *
 * @author ASUS
 */
public class UserInjector {
    
    public static void main(String[] args) {
        AuthService userService = new AuthService();
        userService.registerUser("Administrator", "admin", "123");
    }
}
