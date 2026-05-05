package absensi;

import Koneksi.MongoManager;
import sipenta.dao.Karyawan; 
import sipenta.object.LogAbsensi;
import sipenta.dao.GenericDAO;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter; 
import java.util.Scanner;

public class TesScan {

    public static void main(String[] args) {

        // 1. Inisialisasi Database
        MongoDatabase db = MongoManager.getDatabase();
        
        // 2. Inisialisasi DAO
        GenericDAO<Karyawan> karyawanDAO = new GenericDAO<>("karyawan", Karyawan.class);
        GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("LogAbsensi", LogAbsensi.class);

        Scanner input = new Scanner(System.in);

        System.out.println("=== SIMULASI SCAN RFID SIPENTA ===");
        System.out.print("Scan RFID: ");
        String rfidInput = input.nextLine();

        try {
            // 3. PENCARIAN
            Karyawan karyawan = karyawanDAO.findOne(Filters.eq("rfid", rfidInput));

            if (karyawan != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String waktuBersih = LocalTime.now().format(formatter);
                // -------------------------------------

                // 4. Membuat objek LogAbsensi dengan waktu yang sudah rapi
                LogAbsensi log = new LogAbsensi(
                        karyawan.getNama(), 
                        LocalDate.now().toString(),
                        waktuBersih, // Menggunakan waktuBersih (tanpa nanodetik)
                        "hadir"
                );

                // 5. Simpan ke MongoDB
                logDAO.save(log);

                System.out.println("\n✅ ABSENSI BERHASIL!");
                System.out.println("Nama    : " + karyawan.getNama());
                System.out.println("Jabatan : " + karyawan.getJabatan());
                System.out.println("Waktu   : " + waktuBersih);

            } else {
                System.out.println("\n❌ Kartu [" + rfidInput + "] tidak dikenali!");
            }
        } catch (Exception e) {
            System.err.println("\n❌ Terjadi kesalahan: " + e.getMessage());
        }
    }
}