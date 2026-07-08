/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import Sipenta.dao.GenericDAO;
import Sipenta.object.Karyawan;
import Sipenta.object.LogAbsensi;
import com.mongodb.client.model.Filters;
import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class KehadiranService {
    private GenericDAO<Karyawan> daoKaryawan =
            new GenericDAO<>("karyawan", Karyawan.class);

    private GenericDAO<LogAbsensi> daoLog =
            new GenericDAO<>("LogAbsensi", LogAbsensi.class);

    public Karyawan cariKaryawan(String uid){

        return daoKaryawan.findOne(
            Filters.eq("rfid", uid)
        );

    }
    public void simpanAbsensi(String uid){

        LogAbsensi log = new LogAbsensi();

        log.setUidRfid(uid);
        log.setStatus("Hadir");
        log.setWaktuTap(LocalDateTime.now());

        daoLog.save(log);
    }
}
