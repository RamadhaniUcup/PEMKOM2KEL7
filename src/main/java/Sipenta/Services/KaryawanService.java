/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.Services;

import Sipenta.View.AdminPage;
import sipenta.dao.GenericDAO;
import com.mongodb.client.model.Filters;
import Sipenta.Util.EncryptionUtils;
import Sipenta.Util.SecurityUtils;
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
    // Menggunakan koleksi "karyawan" dan referensi Class Karyawan
    private final GenericDAO<Karyawan> DAO;

    public KaryawanService() {
        this.DAO = new GenericDAO<>("karyawan", Karyawan.class);
    }

    /*
     * Mengecek apakah sebuah string sudah berbentuk hash SHA-256.
     * Hash SHA-256 biasanya panjangnya 64 karakter hexadecimal.
     */
    private boolean isSha256Hash(String value) {
        return value != null && value.matches("^[a-fA-F0-9]{64}$");
    }

    /*
     * RFID dibuat satu arah menggunakan SHA-256.
     * Jika RFID sudah berupa hash, maka tidak di-hash ulang.
     */
    private String hashRfidIfNeeded(String rfid) {
        if (rfid == null || rfid.isEmpty()) {
            return rfid;
        }

        if (isSha256Hash(rfid)) {
            return rfid;
        }

        return SecurityUtils.getHash(rfid, SecurityUtils.SHA_256);
    }

    /*
     * NIP didekripsi saat ingin ditampilkan.
     * Jika gagal decrypt, maka akan mengembalikan data aslinya.
     */
    private String decryptNip(String nip) {
        if (nip == null || nip.isEmpty()) {
            return nip;
        }

        String hasilDecrypt = EncryptionUtils.decrypt(nip);

        if (hasilDecrypt == null) {
            return nip;
        }

        return hasilDecrypt;
    }

    /**
     * 1.CREATE: Fungsi untuk menyimpan data karyawan baru ke MongoDB
     *
     * @param karyawanBaru
     */
    public void tambahKaryawan(Karyawan karyawanBaru) {
        String rfidHash = hashRfidIfNeeded(karyawanBaru.getRfid());
        String nipEncrypt = EncryptionUtils.encrypt(karyawanBaru.getNip());

        karyawanBaru.setRfid(rfidHash);
        karyawanBaru.setNip(nipEncrypt);

        DAO.save(karyawanBaru);
    }

    public void tambahKaryawan(String rfid, String nip, String nama, String jabatan) {
        String rfidHash = hashRfidIfNeeded(rfid);
        String nipEncrypt = EncryptionUtils.encrypt(nip);

        Karyawan karyawanBaru = new Karyawan(rfidHash, nipEncrypt, nama, jabatan);

        DAO.save(karyawanBaru);
    }

    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan
     */
    public void tampilkanDaftarKaryawan() {
        List<Karyawan> daftar = DAO.findAll();

        System.out.println("--- Daftar Karyawan ---");

        for (Karyawan k : daftar) {
            System.out.println(k.toString());
        }
    }

    /**
     * 2.READ (All): Fungsi untuk mengambil semua data karyawan
     *
     * @param panelTarget
     * @param key
     */
    public void tampilKaryawan(JPanel panelTarget, String key) {
        List<Karyawan> daftarKaryawan;

        if (key.isEmpty()) {
            daftarKaryawan = DAO.findAll();
        } else {
            daftarKaryawan = cariKaryawan(key);
        }

        panelTarget.removeAll();

        panelTarget.setLayout(new BorderLayout());
        panelTarget.setBackground(new Color(15, 23, 42));

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            for (Karyawan k : daftarKaryawan) {

                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 0));
                cardPanel.setBackground(new Color(255, 255, 255));

                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 41, 59), 1, true),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));

                JLabel lblNama = new JLabel("Nama: " + k.getNamaLengkap());
                lblNama.setForeground(new Color(33, 37, 41));

                JLabel lblIDK = new JLabel("ID Karyawan: " + k.getId());
                lblIDK.setForeground(new Color(33, 37, 41));

                JLabel lbljab = new JLabel("Jabatan: " + k.getJabatan());
                lbljab.setForeground(new Color(33, 37, 41));

                String nipAsli = decryptNip(k.getNip());
                JLabel lblnip = new JLabel("NIP: " + nipAsli);
                lblnip.setForeground(new Color(33, 37, 41));

                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 12, 0));
                controlPanel.setBackground(new Color(255, 255, 255));

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(59, 130, 246));
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));

                tombolEdit.addActionListener((ActionEvent e) -> {
                    AdminPage.idKaryawanEdit = k.getId();

                    /*
                     * RFID sudah di-hash, jadi tidak bisa dikembalikan ke RFID asli.
                     * Karena itu field UID dibuat tidak bisa diedit.
                     */
                    AdminPage.txtUid.setText(k.getRfid());
                    AdminPage.txtUid.setEnabled(false);

                    /*
                     * NIP bisa didecrypt karena menggunakan AES.
                     */
                    AdminPage.txtnip.setText(nipAsli);
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
                            null,
                            "Apakah Anda ingin menghapus data " + k.getNamaLengkap() + "?",
                            "Konfirmasi Pengelolaan",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
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

                cardPanel.add(lblNama);
                cardPanel.add(lblIDK);
                cardPanel.add(lbljab);
                cardPanel.add(lblnip);
                cardPanel.add(controlPanel);

                gridPanel.add(cardPanel);
            }

            panelTarget.add(gridPanel, BorderLayout.NORTH);

            panelTarget.revalidate();
            panelTarget.repaint();

        } catch (Exception e) {
            System.err.println("Error saat menampilkan data karyawan: " + e.getMessage());
        }
    }

    /**
     * 3.READ: Mencari data karyawan berdasarkan keyword.
     *
     * @param key
     * @return
     */
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();

        for (Field field : Karyawan.class.getDeclaredFields()) {

            /*
             * RFID dan NIP tidak dipakai untuk pencarian biasa
             * karena RFID sudah di-hash dan NIP sudah dienkripsi.
             */
            if (field.getName().equals("uidRfid")
                    || field.getName().equals("rfid")
                    || field.getName().equals("nip")) {
                continue;
            }

            filters.add(Filters.regex(field.getName(), key, "i"));
        }

        if (filters.isEmpty()) {
            return DAO.findAll();
        }

        return DAO.findMany(Filters.or(filters));
    }

    /**
     * Method ini dipakai untuk proses tap kartu.
     * RFID asli dari kartu akan di-hash dulu, lalu dicari di database.
     *
     * @param rfidAsli
     * @return
     */
    public Karyawan findByRfid(String rfidAsli) {
        String rfidHash = hashRfidIfNeeded(rfidAsli);

        Bson filter = Filters.eq("rfid", rfidHash);

        return DAO.findOne(filter);
    }

    /**
     * 4.UPDATE: Memperbarui data karyawan menggunakan filter ObjectId.
     *
     * @param newK
     */
    public void updateKaryawan(Karyawan newK) {
        Bson filter = Filters.eq("_id", new org.bson.types.ObjectId(newK.getId()));

        Karyawan k = DAO.findOne(filter);

        if (k != null) {
            String rfidHash = hashRfidIfNeeded(newK.getRfid());
            String nipEncrypt = EncryptionUtils.encrypt(newK.getNip());

            newK.setRfid(rfidHash);
            newK.setNip(nipEncrypt);

            DAO.update(filter, newK);

            AdminPage.showData("");
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }
    }

    /**
     * 5.DELETE: Menghapus data karyawan dari database
     *
     * @param idK
     */
    public void hapusKaryawan(String idK) {
        Bson filter = Filters.eq("_id", new org.bson.types.ObjectId(idK));

        DAO.delete(filter);

        AdminPage.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }

}