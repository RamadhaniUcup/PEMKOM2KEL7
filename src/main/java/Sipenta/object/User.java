/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.object;

import java.time.LocalDateTime;


public class User {
    
    private String username;
    private String password;
    private String nama_lengkap;
    private LocalDateTime lastLogin;

    // Klik kanan > Insert Code > Getter and Setter (Pilih semua)
    public User() {   
    }

    public User(String fullname, String username, String password, LocalDateTime lastLogin) {
        this.nama_lengkap = fullname;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }
    
    // Getter & Setter Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    // Getter & Setter Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Getter & Setter Nama Lengkap
    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }
    
    
    public String getFullname() {
        return nama_lengkap;
    }

    public void setFullname(String fullname) {
        this.nama_lengkap = fullname;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nama_lengkap='" + nama_lengkap + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}