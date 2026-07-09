/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18nService {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    
    // Interface untuk mendaftarkan UI yang ingin mendengarkan perubahan bahasa
    public interface I18nChangeListener {
        void onLanguageChanged();
    }
    
    private static final List<I18nChangeListener> listeners = new ArrayList<>();
    
    // Default bahasa saat aplikasi pertama kali menyala
    static {
        setLocale(new Locale("id"));
    }
    
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        // Membaca file kamus di package "i18n" dengan awalan "messages"
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
        notifyListeners();
    }
    
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!"; 
        }
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    
    public static synchronized void registerListener(I18nChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public static synchronized void unregisterListener(I18nChangeListener listener) {
        listeners.remove(listener);
    }
    
    private static void notifyListeners() {
        for (I18nChangeListener listener : listeners) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }
}
