/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import java.util.List;
import com.mongodb.client.model.Filters;
import sipenta.dao.GenericDAO;
import Sipenta.object.Karyawan;
import javax.swing.JPanel;
import org.bson.conversions.Bson;

public class KaryawanService {

    // Variabel dao harus tahu dia membawa objek Karyawan
    private GenericDAO<Karyawan> dao;

    public KaryawanService() {

        // Panggil dengan Class dan nama koleksi
            this.dao = new GenericDAO<>("karyawan", Karyawan.class);
    }
    public List<Karyawan> search(String keyword) {
        Bson filter = Filters.or(
            Filters.regex("nama", keyword, "i"),
            Filters.regex("nip", keyword, "i")
            
        );
        return dao.find(filter);
    }

    public List<Karyawan> findAll() {
        return dao.findAll();
    }

    public void save(Karyawan k) {
        dao.save(k);
    }

    public void update(Karyawan k, String nip) {
        dao.update(Filters.eq("nip", nip), k);
    }

    public void delete(String nip) {
        dao.delete(Filters.eq("nip", nip));
    }

    public void tampilKaryawan(JPanel jPanel4, String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void updateKaryawan(Karyawan K) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void tambahKaryawan(Karyawan K) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Karyawan> tampilKaryawan(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteKaryawan(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
