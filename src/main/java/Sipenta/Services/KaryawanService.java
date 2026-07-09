package Sipenta.Services;

import Sipenta.View.AdminPanel;
import Sipenta.Util.EncryptionUtils;
import sipenta.dao.GenericDAO;
import sipenta.dao.Karyawan;

import com.mongodb.client.model.Filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class KaryawanService {

    private final GenericDAO<Karyawan> DAO;

    public KaryawanService() {
        DAO = new GenericDAO<>("karyawan", Karyawan.class);
    }

    // =====================================================
    // SAVE
    // =====================================================
    public void tambahKaryawan(Karyawan k) {

        if (k.getNip() != null && !k.getNip().isEmpty()) {
            String encrypt = EncryptionUtils.encrypt(k.getNip());

            if (encrypt != null) {
                k.setNip(encrypt);
            }
        }

        DAO.save(k);
    }

    // =====================================================
    // TAMPIL DATA
    // =====================================================
    public void tampilKaryawan(JPanel panelTarget, String key) {

        List<Karyawan> list;

        if (key == null || key.trim().isEmpty()) {
            list = DAO.findAll();
        } else {

            list = DAO.findMany(
                    Filters.or(
                            Filters.regex("nama", key, "i"),
                            Filters.regex("jabatan", key, "i"),
                            Filters.regex("uid", key, "i")
                    )
            );
        }

        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());

        // Warna tema, senada dengan sidebar aplikasi (navy)
        final Color NAVY_ACCENT  = new Color(66, 133, 244);
        final Color ACCENT_HOVER = new Color(40, 100, 220);
        final Color BG_SOFT      = new Color(15, 15, 45);
        final Color DANGER       = new Color(234, 67, 53);
        final Color DANGER_HOVER = new Color(200, 45, 35);

        JPanel listWrap = new JPanel();
        listWrap.setLayout(new BoxLayout(listWrap, BoxLayout.Y_AXIS));
        listWrap.setBackground(BG_SOFT);
        listWrap.setBorder(new EmptyBorder(16, 16, 16, 16));

        for (Karyawan k : list) {

            String nipDecrypt = k.getNip();
            if (nipDecrypt != null) {
                String hasil = EncryptionUtils.decrypt(nipDecrypt);
                if (hasil != null) {
                    nipDecrypt = hasil;
                }
            }
            final String nip = nipDecrypt;

            // ===== Kartu utama =====
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            card.setMaximumSize(new Dimension(560, 160));
            card.setBorder(new EmptyBorder(16, 20, 16, 20));

            // ===== Baris 1: Nama | ID Karyawan =====
            JPanel row1 = new JPanel(new GridLayout(1, 2));
            row1.setOpaque(false);
            row1.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

            JLabel lblNama = new JLabel("Nama: " + safe(k.getNama()));
            lblNama.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblNama.setForeground(new Color(30, 30, 30));

            JLabel lblIdKaryawan = new JLabel("ID Karyawan: " + safe(k.getIdKaryawan()));
            lblIdKaryawan.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblIdKaryawan.setForeground(new Color(30, 30, 30));

            row1.add(lblNama);
            row1.add(lblIdKaryawan);

            // ===== Baris 2: Jabatan | NIP =====
            JPanel row2 = new JPanel(new GridLayout(1, 2));
            row2.setOpaque(false);
            row2.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

            JLabel lblJabatan = new JLabel("Jabatan: " + safe(k.getJabatan()));
            lblJabatan.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblJabatan.setForeground(new Color(30, 30, 30));

            JLabel lblNip = new JLabel("NIP: " + safe(nip));
            lblNip.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblNip.setForeground(new Color(30, 30, 30));

            row2.add(lblJabatan);
            row2.add(lblNip);

            card.add(row1);
            card.add(Box.createVerticalStrut(6));
            card.add(row2);
            card.add(Box.createVerticalStrut(16));

            // ===== Tombol Edit & Delete =====
            JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            actionRow.setOpaque(false);
            actionRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            actionRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

            JButton edit = buatTombolRounded("Edit", NAVY_ACCENT, ACCENT_HOVER);
            JButton hapus = buatTombolRounded("Delete", DANGER, DANGER_HOVER);

            edit.addActionListener(e -> {

                AdminPanel.idKaryawanEdit = k.getIdHex();
                AdminPanel.txtUid.setText(k.getUid());
                AdminPanel.txtnip.setText(nip);
                AdminPanel.txtkaryawan.setText(k.getNama());
                AdminPanel.txtJabatan.setSelectedItem(k.getJabatan());

                // UID dikunci saat mode edit, karena UID sudah terikat
                // ke kartu RFID fisik dan tidak boleh diubah sembarangan
                AdminPanel.txtUid.setEditable(false);
                AdminPanel.txtUid.setEnabled(false);

                AdminPanel.btnUpdate.setEnabled(true);
                AdminPanel.btnSimpan.setEnabled(false);

            });

            hapus.addActionListener(e -> {

                hapusKaryawan(k.getIdHex());

            });

            actionRow.add(edit);
            actionRow.add(hapus);
            card.add(actionRow);

            listWrap.add(card);
            listWrap.add(Box.createVerticalStrut(14));
        }

        panelTarget.add(listWrap, BorderLayout.NORTH);

        panelTarget.revalidate();
        panelTarget.repaint();

    }

    // Tombol rounded kecil (biru untuk Edit, merah untuk Delete)
    private JButton buatTombolRounded(String teks, Color warnaDasar, Color warnaHover) {

        JButton btn = new JButton(teks) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? warnaHover : warnaDasar);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setPreferredSize(new Dimension(80, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setRolloverEnabled(true);

        return btn;
    }

    // Bantu tampilkan "-" kalau data null/kosong
    private String safe(String val) {
        return (val == null || val.isEmpty()) ? "-" : val;
    }



    // =====================================================
    // UPDATE
    // =====================================================
    public void updateKaryawan(Karyawan k) {

        if (k.getNip() != null && !k.getNip().isEmpty()) {

            String encrypt = EncryptionUtils.encrypt(k.getNip());

            if (encrypt != null) {
                k.setNip(encrypt);
            }
        }

        Bson filter = Filters.eq("_id", k.getId());

        DAO.update(filter, k);

        AdminPanel.showData("");

    }

    // =====================================================
    // DELETE
    // =====================================================
    public void hapusKaryawan(String id) {

        DAO.delete(Filters.eq("_id", new ObjectId(id)));

        AdminPanel.showData("");

    }

    // =====================================================
    // LOGIN RFID
    // =====================================================
    public Karyawan getKaryawanByNip(String nipAsli) {

        try {

            String encrypt = EncryptionUtils.encrypt(nipAsli);

            if (encrypt == null) {
                encrypt = nipAsli;
            }

            return DAO.findOne(Filters.eq("nip", encrypt));

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }

}