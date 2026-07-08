/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author ASUS
 */
public class I18nService {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    
    // Interface untuk mendaftarkan UI yang ingin mendengarkan perubahan bahasa
    public interface I18nChangeListener {
        void onLanguageChanged();
    }
    
    // Daftar semua form/panel yang sedang aktif mendengarkan perubahan
    private static final List<I18nChangeListener> listeners = new ArrayList<>();
    
    // Blok inisialisasi default agar tidak NullPointerException di awal aplikasi
    static {
        setLocale(new Locale("id")); 
    }
    
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        
        // Pastikan folder resources kamu bernama i18n
        // contoh:
        // src/i18n/messages_id.properties
        // src/i18n/messages_en.properties
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);

        // Update semua form yang sudah didaftarkan
        notifyListeners();
    }
    
    /**
     * Mengambil teks berdasarkan key
     *
     * @param key Key pada file properties
     * @return String hasil terjemahan
     */
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!";
        }
    }
    
    /**
     * Mengambil locale yang sedang aktif
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * Mendaftarkan form agar otomatis berubah bahasa
     */
    public static synchronized void registerListener(I18nChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Menghapus form dari daftar listener
     */
    public static synchronized void unregisterListener(I18nChangeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Memberitahu semua form bahwa bahasa telah berubah
     */
    private static void notifyListeners() {
        for (I18nChangeListener listener : listeners) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }    
}
