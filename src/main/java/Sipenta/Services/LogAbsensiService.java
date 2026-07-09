package Sipenta.Services;

import sipenta.dao.GenericDAO;
import sipenta.object.LogAbsensi;
import java.util.List;

/**
 * Service untuk menangani data Log Absensi dari MongoDB
 */
public class LogAbsensiService {
    
    private final GenericDAO<LogAbsensi> DAO;

    public LogAbsensiService() {
        // Nama koleksi harus sama persis dengan yang ada di MongoDB Compass
        this.DAO = new GenericDAO<>("LogAbsensi", LogAbsensi.class);
    }

    /**
     * Mengambil semua data log absensi dari database
     * @return List data log
     */
    public List<LogAbsensi> getAllLog() {
        return DAO.findAll();
    }
}