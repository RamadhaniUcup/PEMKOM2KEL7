package Sipenta.Services;

import Sipenta.object.User;
import sipenta.dao.GenericDAO;
import Sipenta.View.AdminPage;
import Sipenta.View.LoginPage;
import Sipenta.Util.SecurityUtils;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class AuthService {

    // DAO untuk collection users
    private final GenericDAO<User> userDAO = new GenericDAO<>("users", User.class);

    /**
     * Proses login admin/user.
     *
     * @param username
     * @param plainPassword
     * @param loginPage
     */
    public void login(String username, String plainPassword, LoginPage loginPage) {

        if (username == null || username.trim().isEmpty()
                || plainPassword == null || plainPassword.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Username dan password tidak boleh kosong!",
                    "Login Gagal",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Mengubah password input menjadi hash SHA-256
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        // Mencari user berdasarkan username dan password hash
        User user = userDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));

        if (user != null) {
            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(Filters.eq("username", username), user);

            JOptionPane.showMessageDialog(null, "Selamat Datang, " + user.getFullname());

            AdminPage admPage = new AdminPage();
            admPage.setLocationRelativeTo(null);
            admPage.setVisible(true);
            admPage.setExtendedState(Frame.MAXIMIZED_BOTH);

            loginPage.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Register user/admin baru.
     *
     * @param fullname
     * @param username
     * @param plainPassword
     */
    public void registerUser(String fullname, String username, String plainPassword) {

        if (fullname == null || fullname.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || plainPassword == null || plainPassword.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Fullname, username, dan password tidak boleh kosong!",
                    "Register Gagal",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek apakah username sudah ada
        User existingUser = userDAO.findOne(Filters.eq("username", username));

        if (existingUser != null) {
            JOptionPane.showMessageDialog(null,
                    "Username sudah digunakan!",
                    "Register Gagal",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hash password menggunakan SHA-256
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        User newUser = new User(fullname, username, hashedPassword, null);

        try {
            userDAO.save(newUser);

            JOptionPane.showMessageDialog(null,
                    "User berhasil didaftarkan!",
                    "Register Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal mendaftarkan user: " + e.getMessage(),
                    "Register Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}