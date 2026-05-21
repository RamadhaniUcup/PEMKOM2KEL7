/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Sipenta.View.AdminPage;
import sipenta.dao.GenericDAO;
import com.mongodb.client.model.Filters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.conversions.Bson;
import sipenta.dao.Karyawan;

/**
 *
 * @author mnish
 */
public class KaryawanService {

    // Inisialisasi GenericDAO khusus untuk entitas Karyawan
    // Menggunakan koleksi "karyawan" dan referensi Class Karyawan [3]
    private final GenericDAO<Karyawan> DAO;

    public KaryawanService() {
        this.DAO = new GenericDAO<>("karyawan", Karyawan.class);
    }

    /**
     * 1.CREATE: Fungsi untuk menyimpan data karyawan baru ke MongoDB [2], [3]
     *
     * @param karyawanBaru
     */
    public void tambahKaryawan(Karyawan karyawanBaru) {
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    public void tambahKaryawan(String rfid, String nip, String nama, String jabatan) {
        Karyawan karyawanBaru = new Karyawan(rfid, nip, nama, jabatan);
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     */
    public void tampilkanDaftarKaryawan() {
        List<Karyawan> daftar = DAO.findAll();
        System.out.println("--- Daftar Karyawan Bank ---");
        for (Karyawan k : daftar) {
            System.out.println(k.toString()); // Menggunakan format toString di sumber [7]
        }
    }

    /**
     * 2.READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     *
     * @param panelTarget
     * @param key
     */
    public void tampilKaryawan(JPanel panelTarget, String key) {
        //1. 
        // Menampilkan data berdasarkan request
        // key "null/kosong" = get all data
        // key "filled" = get specific data

        List<Karyawan> daftarKaryawan;
        if (key.isEmpty()) {
            //Mengambil data dari database menggunakan GenericDAO
            daftarKaryawan = DAO.findAll();
        } else {
            //Mengambil data dari database menggunakan GenericDAO
            //berdasarkan kata kunci yang diketik
            daftarKaryawan = cariKaryawan(key);
        }
        // 2. Membersihkan panel target utama sebelum memuat data baru
        panelTarget.removeAll();

        // Mengubah layout panel target menjadi BorderLayout
        panelTarget.setLayout(new BorderLayout());
        // Mengatur warna background utama menjadi biru
        panelTarget.setBackground(new Color(15, 23, 42));

        // Membuat panel grid khusus untuk menampung kotak/card
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false); // Transparan agar warna biru panelTarget terlihat
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Memberi jarak dari tepi layar

        // 3. Iterasi data dan menambahkannya ke panel grid
        try {
            for (Karyawan k : daftarKaryawan) {
                // Membuat panel 'Card' (box orange) untuk 1 karyawan
                // Layout 4 baris 1 kolom agar kolor berisi Nama,ID, Departemen, panel control 
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 0));
                cardPanel.setBackground(new Color(255, 255, 255)); // Warna background orange

                // Memberikan garis tepi tipis membulat (rounded) dan padding/jarak ke dalam
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 41, 59), 1, true),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));

                // Membuat Label Nama & Set warna teks jadi Putih
                JLabel lblNama = new JLabel("Nama: " + k.getNamaLengkap());
                lblNama.setForeground(new Color(33, 37, 41));

                // Membuat Label ID Karyawan & Set warna teks jadi Putih
                JLabel lblIDK = new JLabel("ID Karyawan: " + k.getId());
               lblIDK.setForeground(new Color(33, 37, 41));

                // Membuat Label Departemen & Set warna teks jadi Putih
                JLabel lbljab = new JLabel("Jabatan: " + k.getJabatan());
                lbljab.setForeground(new Color(33, 37, 41));
                // Membuat Label Departemen & Set warna teks jadi Putih
                JLabel lblnip = new JLabel("Nip:"+ k.getNip());
                lblnip.setForeground(new Color(33, 37, 41));

                // Membuat panel kontrol 1 baris 2 kolom, berisi tombol edit dan hapus
                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 12, 0));
                controlPanel.setBackground(new Color(255, 255, 255));

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(59, 130, 246));
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addActionListener((ActionEvent e) -> {
                    AdminPage.idKaryawanEdit = k.getId();
                    AdminPage.txtUid.setText(k.getRfid());
                    AdminPage.txtnip.setText(k.getNip());
                    AdminPage.txtnip.setEnabled(false); 
                    AdminPage.txtkaryawan.setText(k.getNama());
                    AdminPage.txtJabatan.setSelectedItem(k.getJabatan());
                    AdminPage.btnUpdate.setEnabled(true);
                    AdminPage.btnSimpan.setEnabled(false);
                     
                });
                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setBackground(new Color(239, 68, 68));
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                            null, // Parent component
                            "Apakah Anda ingin menyimpan data "+k.getNamaLengkap()+"?", // Message
                            "Konfirmasi Pengelolaan", // Title
                            JOptionPane.YES_NO_OPTION, // Option type
                            JOptionPane.QUESTION_MESSAGE, // Message type
                            null, // Custom icon (null uses default)
                            options, // The array of custom button text
                            options[0] // Default button focused
                    );

                    switch (choice) {
                        case JOptionPane.YES_OPTION -> hapusKaryawan(k.getId());
                        case JOptionPane.NO_OPTION -> System.out.println("User memilih: Batal");
                        default -> {
                        }
                    }
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                // Memasukkan label ke dalam cardPanel (box orange)
                cardPanel.add(lblNama);
                cardPanel.add(lblIDK);
                cardPanel.add(lbljab);
                cardPanel.add(lblnip);
                cardPanel.add(controlPanel);

                // Memasukkan cardPanel utuh ke dalam gridPanel
                gridPanel.add(cardPanel);
            }

            // Memasukkan gridPanel ke bagian ATAS (NORTH) dari panel target.
            panelTarget.add(gridPanel, BorderLayout.NORTH);

            // 4. Me-refresh panel agar perubahan muncul di GUI
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
        }
    }

    /**
     * 3.READ (One): Mencari satu karyawan spesifik berdasarkan UID RFID [5],
     * [6] Sangat krusial untuk alur Tap Kartu pada Pertemuan 14 [8].
     *
     * @param key
     * @return
     */
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();
        // Get all fields from the Karyawan class
        for (Field field : Karyawan.class.getDeclaredFields()) {
            // Skip the uidRfid field and non-string fields if necessary
            if (field.getName().equals("uidRfid")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        // Search and return Karyawan objects directly
        List<Karyawan> results = DAO.findMany(Filters.or(filters));
        return results;
    }

    /**
     * 4.UPDATE: Memperbarui data karyawan menggunakan filter Bson [5], [6]
     *
     * @param newK
     */
    public void updateKaryawan(Karyawan newK) {
        Bson filter = Filters.eq("_id", new org.bson.types.ObjectId(newK.getId()));
        Karyawan k = DAO.findOne(filter);
        if (k != null) {
            DAO.update(filter, newK);
            AdminPage.showData("");
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }
    }

    /**
     * 5.DELETE: Menghapus data karyawan dari database [5], [6]
     *
     * @param idK
     */
    public void hapusKaryawan(String idK) {
        // Ubah "id" menjadi "_id" dan konversi idK menjadi ObjectId
        Bson filter = Filters.eq("_id", new org.bson.types.ObjectId(idK)); 
        DAO.delete(filter); 
        AdminPage.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }

}
