/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Sipenta.ui.Jframs;


import Sipenta.Services.LogAbsensiService;
import Sipenta.Services.KaryawanService;
import Sipenta.Util.EncryptionUtils;
import sipenta.dao.Karyawan;
import sipenta.object.LogAbsensi;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class PanelLogAbsensi extends javax.swing.JPanel {

    private static PanelLogAbsensi activeInstance;

    // Panel header tetap (tidak ikut ter-scroll)
    private JPanel panelHeaderTetap;
    // Panel yang menampung baris-baris data (yang ini yang di-scroll)
    private JPanel panelIsiData;
    private JScrollPane scrollPaneData;

    // Warna dasar untuk baris genap & ganjil (zebra striping)
    private static final java.awt.Color WARNA_BARIS_GANJIL = new java.awt.Color(25, 30, 42);
    private static final java.awt.Color WARNA_BARIS_GENAP  = new java.awt.Color(31, 37, 51);
    private static final java.awt.Color WARNA_BORDER       = new java.awt.Color(40, 50, 65);

    public PanelLogAbsensi() {
        initComponents();
        // ==========================================================
        // 3 BARIS INI YANG BIKIN WARNA ABU-ABUNYA HILANG JADI GELAP!
        // ==========================================================
        jScrollPane1.getViewport().setBackground(new java.awt.Color(25, 30, 42));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.setBackground(new java.awt.Color(25, 30, 42)); 
        // ==========================================================
        siapkanAreaScrollData();
        activeInstance = this;

        // Memanggil fungsi untuk menggambar UI List Modern saat panel dibuka
        loadDataKeTabel();
    }

    private void loadDataKeTabel() {
        // Tampilkan semua data tanpa filter awal
        tampilkanDataLog("", null, null);
    }

    public static void refreshJikaTerbuka() {
        if (activeInstance != null) {
            activeInstance.loadDataKeTabel();
        }
    }

    // ========================================================================
    // PERSIAPAN AREA SCROLL: header dipisah dari data agar tetap terlihat
    // saat daftar di-scroll ke bawah.
    // ========================================================================
    private void siapkanAreaScrollData() {
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.setBackground(new java.awt.Color(25, 30, 42));

        // 1. Panel header (statis, selalu terlihat di atas)
        panelHeaderTetap = new JPanel();
        panelHeaderTetap.setLayout(new javax.swing.BoxLayout(panelHeaderTetap, javax.swing.BoxLayout.Y_AXIS));
        panelHeaderTetap.add(buatBarisData("NO", "NAMA", "NIP / ID", "WAKTU", "STATUS", true));

        // 2. Panel isi data (yang akan di-scroll)
        panelIsiData = new JPanel();
        panelIsiData.setLayout(new javax.swing.BoxLayout(panelIsiData, javax.swing.BoxLayout.Y_AXIS));
        panelIsiData.setBackground(new java.awt.Color(25, 30, 42));

        scrollPaneData = new JScrollPane(panelIsiData);
        scrollPaneData.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollPaneData.getViewport().setBackground(new java.awt.Color(25, 30, 42));
        scrollPaneData.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Scroll lebih halus (per 16px, bukan per baris kasar)
        JScrollBar vBar = scrollPaneData.getVerticalScrollBar();
        vBar.setUnitIncrement(16);

        jPanel4.add(panelHeaderTetap, java.awt.BorderLayout.NORTH);
        jPanel4.add(scrollPaneData, java.awt.BorderLayout.CENTER);
    }

    // ========================================================================
    // KODINGAN BARU: MENAMPILKAN DATA DENGAN DESAIN MODERN (TANPA TABEL)
    // ========================================================================
    public void tampilkanDataLog(String keyword, java.time.LocalDate tglMulai, java.time.LocalDate tglSelesai) {
        // 1. Bersihkan hanya kontainer data (header tidak disentuh)
        panelIsiData.removeAll();

        // 2. Ambil data
        LogAbsensiService service = new LogAbsensiService();
        KaryawanService karyawanService = new KaryawanService();
        List<LogAbsensi> daftarLog = service.getAllLog();

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int nomor = 1;
        for (LogAbsensi log : daftarLog) {
            try {
                // --- FILTER TANGGAL ---
                if (tglMulai != null && tglSelesai != null) {
                    java.time.LocalDate tglLog = java.time.LocalDate.parse(log.getTanggal(), formatter);
                    if (tglLog.isBefore(tglMulai) || tglLog.isAfter(tglSelesai)) {
                        continue;
                    }
                }

                String waktuStr = log.getTanggal() + " " + log.getJam_masuk();

                // =========================================================
                // KUNCI PERBAIKAN: BUKA SANDI (DECRYPT) SEBELUM MENCARI
                // =========================================================
                String idDariLog = log.getNip(); // Ambil teks sandi dari log
                String idDecrypted = EncryptionUtils.decrypt(idDariLog); // Buka sandinya

                String namaKaryawan = "Tidak Ditemukan";
                String nipKaryawanTampil = idDariLog; // Tampilan default jika gagal

                // 1. Coba cari pakai data mentah dari log
                Karyawan karyawan = karyawanService.getKaryawanByNip(idDariLog);

                // 2. Jika tidak ketemu, coba cari pakai NIP yang sudah dibuka sandinya
                if (karyawan == null && idDecrypted != null) {
                    karyawan = karyawanService.getKaryawanByNip(idDecrypted);
                }

                // 3. Jika Karyawan ketemu di Database
                if (karyawan != null) {
                    namaKaryawan = karyawan.getNama();

                    // Pastikan NIP yang ditampilkan di layar juga rapi (sudah di-decrypt)
                    String nipKaryawanDB = karyawan.getNip();
                    String nipRapi = EncryptionUtils.decrypt(nipKaryawanDB);
                    nipKaryawanTampil = (nipRapi != null) ? nipRapi : nipKaryawanDB;

                } else if (idDecrypted != null) {
                    // Jika karyawan sudah dihapus dari DB, minimal tampilkan NIP yang rapi
                    nipKaryawanTampil = idDecrypted;
                }
                // =========================================================

                // --- FILTER KEYWORD (PENCARIAN NAMA & NIP) ---
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String lowerKey = keyword.toLowerCase();
                    if (!namaKaryawan.toLowerCase().contains(lowerKey) &&
                        !nipKaryawanTampil.toLowerCase().contains(lowerKey)) {
                        continue;
                    }
                }

                // Tambahkan baris ke area scroll (bukan lagi ke jPanel4 langsung)
                boolean baris_genap = (nomor % 2 == 0);
                panelIsiData.add(buatBarisData(String.valueOf(nomor++), namaKaryawan, nipKaryawanTampil, waktuStr, log.getStatus(), false, baris_genap));

            } catch (Exception e) {
                System.out.println("Error memproses baris log: " + e.getMessage());
            }
        }

        // Jika tidak ada data sama sekali, tampilkan pesan kosong yang rapi
        if (nomor == 1) {
            panelIsiData.add(buatBarisKosong());
        }

        // 4. Segarkan layar agar kotak-kotak datanya tergambar
        panelIsiData.revalidate();
        panelIsiData.repaint();

        // Pastikan scroll kembali ke atas setiap kali data baru dimuat/difilter
        javax.swing.SwingUtilities.invokeLater(() -> scrollPaneData.getVerticalScrollBar().setValue(0));
    }

    // Overload lama tetap ada agar tidak mematahkan pemanggilan header (isHeader=true)
    private JPanel buatBarisData(String no, String nama, String nip, String waktu, String status, boolean isHeader) {
        return buatBarisData(no, nama, nip, waktu, status, isHeader, false);
    }

    // ========================================================================
    // KODINGAN MAGIC PEMBUAT BARIS & KOLOM (UPDATE: kolom lebih ramping + zebra)
    // ========================================================================
    private JPanel buatBarisData(String no, String nama, String nip, String waktu, String status, boolean isHeader, boolean barisGenap) {
        JPanel row = new JPanel();
        row.setLayout(new javax.swing.BoxLayout(row, javax.swing.BoxLayout.X_AXIS));

        java.awt.Color warnaBackground;
        if (isHeader) {
            warnaBackground = new java.awt.Color(18, 22, 32); // header sedikit lebih gelap agar menonjol
        } else {
            warnaBackground = barisGenap ? WARNA_BARIS_GENAP : WARNA_BARIS_GANJIL;
        }
        row.setBackground(warnaBackground);
        row.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, WARNA_BORDER));
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 50));
        row.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        java.awt.Font fontText = isHeader ? new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12) : new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14);
        java.awt.Color colorText = isHeader ? new java.awt.Color(134, 163, 195) : java.awt.Color.WHITE;

        // 1. LEBAR KOLOM (kolom NIP dipersempit dari 250 -> 160,
        //    karena setelah bug decrypt diperbaiki NIP asli jauh lebih pendek)
        row.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(20, 0)));
        row.add(buatKolom(no, fontText, colorText, 50));
        row.add(buatKolom(nama, fontText, colorText, 220)); // Kolom Nama diperlebar sedikit

        java.awt.Color nipColor = isHeader ? colorText : new java.awt.Color(80, 150, 255);
        row.add(buatKolom(nip, fontText, nipColor, 160)); // Kolom NIP lebih ramping

        row.add(buatKolom(waktu, fontText, colorText, 180));

        // 2. KOLOM STATUS
        JPanel panelStatus = new JPanel();
        panelStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, isHeader ? 0 : 12));
        panelStatus.setOpaque(false);
        panelStatus.setPreferredSize(new java.awt.Dimension(120, 50));
        panelStatus.setMinimumSize(new java.awt.Dimension(120, 50));
        panelStatus.setMaximumSize(new java.awt.Dimension(120, 50));

        if (isHeader) {
            panelStatus.add(buatKolom(status, fontText, colorText, 120));
        } else {
            JLabel lblStatus = new JLabel("  " + status + "  ");
            lblStatus.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            lblStatus.setForeground(java.awt.Color.WHITE);
            lblStatus.setOpaque(true);

            if (status.equalsIgnoreCase("Hadir") || status.equalsIgnoreCase("Masuk") || status.equalsIgnoreCase("IN")) {
                lblStatus.setBackground(new java.awt.Color(25, 135, 84));
            } else {
                lblStatus.setBackground(new java.awt.Color(220, 53, 69));
            }
            panelStatus.add(lblStatus);
        }
        row.add(panelStatus);

        return row;
    }

    // Baris penanda saat data hasil pencarian/filter kosong
    private JPanel buatBarisKosong() {
        JPanel row = new JPanel();
        row.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        row.setBackground(WARNA_BARIS_GANJIL);
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));

        JLabel lbl = new JLabel("Tidak ada data absensi pada rentang / kata kunci ini.");
        lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 13));
        lbl.setForeground(new java.awt.Color(134, 163, 195));
        row.add(lbl);
        return row;
    }

    private JLabel buatKolom(String text, java.awt.Font font, java.awt.Color color, int width) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);

        // 3. RAHASIA KERAPIAN: Kunci dimensi komponen dengan memaksa setMinimumSize
        label.setPreferredSize(new java.awt.Dimension(width, 50));
        label.setMinimumSize(new java.awt.Dimension(width, 50));
        label.setMaximumSize(new java.awt.Dimension(width, 50));
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER); // Paksa teks berada lurus di tengah vertikal

        return label;
    }



    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Log Absensi Karyawan");

        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setText("Daftar Riwayat Presensi Masuk dan Keluar");

        txtCari.setBackground(new java.awt.Color(0, 102, 153));
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Filter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());

        jSpinner2.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(69, 69, 69)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 983, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
      // Cukup panggil tampilkanDataLog dengan menyertakan teks yang dicari
        tampilkanDataLog(txtCari.getText(), null, null);
    
    
    
    }//GEN-LAST:event_txtCariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     eksekusiFilterLaporan(); // Memanggil fungsi filter saat tombol diklik
    }

    
    // =========================================================================
    // TAMBAHKAN DUA FUNGSI INI AGAR ERROR MERAH HILANG
    // =========================================================================
 
 private void eksekusiFilterLaporan() {
        if (jSpinner1.getValue() != null && jSpinner2.getValue() != null) {
            java.util.Date dateMulai = (java.util.Date) jSpinner1.getValue();
            java.util.Date dateSelesai = (java.util.Date) jSpinner2.getValue();

            // Konversi dari java.util.Date ke java.time.LocalDate
            java.time.LocalDate tglMulai = dateMulai.toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            java.time.LocalDate tglSelesai = dateSelesai.toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            tampilkanDataLog(txtCari.getText(), tglMulai, tglSelesai);
        }



    

    
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}

