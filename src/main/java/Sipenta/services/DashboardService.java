/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import Sipenta.dao.GenericDAO;
import Sipenta.object.Karyawan;
import Sipenta.object.LogAbsensi;
import com.mongodb.client.model.Filters;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class DashboardService {
    
    private GenericDAO<Karyawan> dao =
            new GenericDAO<>("karyawan", Karyawan.class);
    
    public long getTotalKaryawan() {
        return dao.count();
    }
    
    public long getTotalHadir() {

    GenericDAO<LogAbsensi> dao =
            new GenericDAO<>("LogAbsensi", LogAbsensi.class);

    String hariIni = LocalDate.now().toString();

    return dao.count(Filters.and(
            Filters.eq("tanggal", LocalDate.now().toString()),
            Filters.eq("status", "hadir"))
        );
    }
    
    public long getTotalAlpha() {

        long total = getTotalKaryawan();
        long hadir = getTotalHadir();

        return total - hadir;
    }
    
    public void loadLogDashboard(
            JLabel nama1, JLabel nama2, JLabel nama3,
            JLabel uid1, JLabel uid2, JLabel uid3,
            JLabel jab1, JLabel jab2, JLabel jab3,
            JLabel jam1, JLabel jam2, JLabel jam3,
            JLabel status1, JLabel status2, JLabel status3
    )   {
    
        GenericDAO<LogAbsensi> dao =
                new GenericDAO<>("LogAbsensi", LogAbsensi.class);

        List<LogAbsensi> list = dao.findAll();

        JLabel[] nama = {nama1, nama2, nama3};
        JLabel[] uid = {uid1, uid2, uid3};
        JLabel[] jab = {jab1, jab2, jab3};
        JLabel[] jam = {jam1, jam2, jam3};
        JLabel[] status = {status1, status2, status3};
        
        for (int i = 0; i < 3; i++) {

            if (i < list.size()) {

                LogAbsensi log = list.get(i);

                nama[i].setText("-");
                uid[i].setText(log.getUidRfid());
                jab[i].setText("-");

                if (log.getWaktuTap() != null) {
                    jam[i].setText(
                            log.getWaktuTap().format(
                                    DateTimeFormatter.ofPattern("HH:mm:ss")
                            )
                    );
                } else {
                    jam[i].setText("-");
                }

                status[i].setText(log.getStatus());

            } else {
                nama[i].setText("-");
                uid[i].setText("-");
                jab[i].setText("-");
                jam[i].setText("-");
                status[i].setText("-");

            }
        }
    }
}

