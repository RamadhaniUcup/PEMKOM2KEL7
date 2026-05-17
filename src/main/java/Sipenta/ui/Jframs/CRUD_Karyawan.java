/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.ui.Jframs;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;

// --- PERBAIKAN JALUR IMPORT YUSUF ---
import Koneksi.MongoManager; // Menghubungkan ke file MongoManager.java milikmu
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 * @author Yusuf Yudha Ramadhani & Naiya
 */
public class CRUD_Karyawan extends javax.swing.JFrame {
    int posisiY = 150;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CRUD_Karyawan.class.getName());

    // Variables declaration - do not modify
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbDepartemen;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDept1;
    private javax.swing.JLabel lblDept2;
    private javax.swing.JLabel lblDept3;
    private javax.swing.JLabel lblID1;
    private javax.swing.JLabel lblID2;
    private javax.swing.JLabel lblID3;
    private javax.swing.JLabel lblNama1;
    private javax.swing.JLabel lblNama2;
    private javax.swing.JLabel lblNama3;
    private javax.swing.JPanel panelData;
    private javax.swing.JScrollPane scrollPanel; // FIXED BY YUSUF: Wadah scroll bar
    private Sipenta.swing.Rounpanel rounpanel1;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUID;

    public CRUD_Karyawan() {
        initComponents();
        tampilData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        rounpanel1 = new Sipenta.swing.Rounpanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtUID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbDepartemen = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelData = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblNama1 = new javax.swing.JLabel();
        lblID1 = new javax.swing.JLabel();
        lblDept1 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblNama2 = new javax.swing.JLabel();
        lblID2 = new javax.swing.JLabel();
        lblDept2 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblNama3 = new javax.swing.JLabel();
        lblID3 = new javax.swing.JLabel();
        lblDept3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        jButton4.setText("jButton4");
        jPanel4.setBackground(new java.awt.Color(255, 153, 51));
        jLabel12.setText("Nama : Nisa");
        jLabel13.setText("ID Karyawan : A1");
        jLabel14.setText("Departemen : Operasional");

        jButton9.setText("Edit");
        jButton10.setText("Delete");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        rounpanel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("UID");
        jLabel2.setText("ID Karyawan");

        cbDepartemen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IT", "Operasional", "Keuangan", "Supervisor" }));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilData();
            }
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel6.setText("🔍");

        javax.swing.GroupLayout rounpanel1Layout = new javax.swing.GroupLayout(rounpanel1);
        rounpanel1.setLayout(rounpanel1Layout);
        rounpanel1Layout.setHorizontalGroup(
            rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rounpanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(33, 33, 33)
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDepartemen, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rounpanel1Layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rounpanel1Layout.setVerticalGroup(
            rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rounpanel1Layout.createSequentialGroup()
                .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rounpanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbDepartemen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))))
                    .addGroup(rounpanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(rounpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        // --- FIXED BY YUSUF: Set up panelData dengan FlowLayout agar kartu membungkus otomatis ---
        panelData.setBackground(new java.awt.Color(0, 0, 102));
        panelData.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));

        // Membungkus panelData ke dalam JScrollPane agar bisa discroll secara vertikal
        scrollPanel = new javax.swing.JScrollPane(panelData);
        scrollPanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rounpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE) // Bagian bawah diganti scrollPanel
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rounpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)) // Bagian bawah diganti scrollPanel
        );

        pack();
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            MongoDatabase db = MongoManager.getDatabase();
            MongoCollection<Document> col = db.getCollection("karyawan");

            Document data = new Document("uid", txtUID.getText())
                .append("id_karyawan", txtID.getText())
                .append("nama", txtNama.getText())
                .append("departemen", cbDepartemen.getSelectedItem().toString());

            col.insertOne(data);
            tampilData();

            txtUID.setText("");
            txtID.setText("");
            txtNama.setText("");
            cbDepartemen.setSelectedIndex(0);
            javax.swing.JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
        } catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal Simpan: " + e.getMessage());
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            MongoDatabase db = MongoManager.getDatabase();
            MongoCollection<Document> col = db.getCollection("karyawan");

            String idCari = txtID.getText(); 
            Document dataBaru = new Document("uid", txtUID.getText())
                .append("id_karyawan", txtID.getText())
                .append("nama", txtNama.getText())
                .append("departemen", cbDepartemen.getSelectedItem().toString());

            com.mongodb.client.result.UpdateResult result = col.replaceOne(
                com.mongodb.client.model.Filters.eq("id_karyawan", idCari), dataBaru
            );
            
            if (result.getMatchedCount() == 0) {
                col.replaceOne(com.mongodb.client.model.Filters.eq("nip", idCari), dataBaru);
            }
            
            javax.swing.JOptionPane.showMessageDialog(this, "Data Karyawan berhasil diperbarui!");
            
            tampilData();
            txtUID.setText("");
            txtID.setText("");
            txtNama.setText("");
            cbDepartemen.setSelectedIndex(0);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        String cari = txtSearch.getText().toLowerCase();
        Component[] cards = panelData.getComponents();

        for(Component c : cards){
            if (c instanceof JPanel) {
                JPanel card = (JPanel) c;
                Component[] isi = card.getComponents();
                boolean ketemu = false;

                for(Component x : isi){
                    if(x instanceof JLabel){
                        JLabel lbl = (JLabel) x;
                        if(lbl.getText().toLowerCase().contains(cari)){
                            ketemu = true;
                        }
                    }
                }
                card.setVisible(ketemu);
            }
        }
        panelData.revalidate();
        panelData.repaint();
    }

    public void tampilData(){
        panelData.removeAll();

        MongoDatabase db = MongoManager.getDatabase();
        MongoCollection<Document> col = db.getCollection("karyawan");
        MongoCursor<Document> cursor = col.find().iterator();

        int jumlahData = 0; // Digunakan untuk menghitung tinggi panel dinamis

        while(cursor.hasNext()){
            Document d = cursor.next();
            jumlahData++;
            
            String nama = d.getString("nama");
            String uid = d.getString("uid") != null ? d.getString("uid") : "-";
            
            String id = "";
            if (d.containsKey("id_karyawan")) {
                id = d.getString("id_karyawan"); 
            } else if (d.containsKey("nip")) {
                id = d.getString("nip"); 
            } else {
                id = "-";
            }
            
            String dept = "";
            if (d.containsKey("departemen")) {
                dept = d.getString("departemen"); 
            } else if (d.containsKey("jabatan")) {
                dept = d.getString("jabatan"); 
            } else {
                dept = "-";
            }

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(350, 135));
            card.setBackground(new Color(153,153,153));
            card.setLayout(null);

            JLabel lUID = new JLabel("UID : " + uid);
            lUID.setBounds(10, 8, 250, 18);

            JLabel lNama = new JLabel("Nama : " + nama);
            lNama.setBounds(10, 30, 250, 18);

            JLabel lID = new JLabel("ID Karyawan : " + id);
            lID.setBounds(10, 52, 250, 18);

            JLabel lDept = new JLabel("Departemen : " + dept);
            lDept.setBounds(10, 74, 300, 18);

            JButton btnEdit = new JButton("Edit");
            btnEdit.setBounds(20, 102, 145, 23);

            final String finalId = id;
            final String finalUid = uid;
            final String finalNama = nama;
            final String finalDept = dept;
            
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtUID.setText(finalUid);
                    txtID.setText(finalId);
                    txtNama.setText(finalNama);
                    cbDepartemen.setSelectedItem(finalDept);
                }
            });

            JButton btnDelete = new JButton("Delete");
            btnDelete.setBounds(175, 102, 145, 23);

            btnDelete.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(null, "Yakin hapus karyawan ID: " + finalId + "?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
                        try {
                            MongoDatabase database = MongoManager.getDatabase();
                            MongoCollection<Document> koleksi = database.getCollection("karyawan");
                            
                            if (d.containsKey("id_karyawan")) {
                                koleksi.deleteOne(com.mongodb.client.model.Filters.eq("id_karyawan", finalId));
                            } else {
                                koleksi.deleteOne(com.mongodb.client.model.Filters.eq("nip", finalId));
                            }
                            
                            javax.swing.JOptionPane.showMessageDialog(null, "Data Berhasil dihapus!");
                            tampilData(); 
                        } catch (Exception e) {
                            javax.swing.JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage());
                        }
                    }
                }
            });

            card.add(lUID);
            card.add(lNama);
            card.add(lID);
            card.add(lDept);
            card.add(btnEdit);
            card.add(btnDelete);
            panelData.add(card);
        }

        // KUNCI UTAMA SCROLL: Mengatur tinggi panelData secara dinamis mengikuti jumlah baris kartu
        int baris = (int) Math.ceil((double) jumlahData / 3); 
        int tinggiPanel = Math.max(480, baris * 145 + 20);
        panelData.setPreferredSize(new Dimension(1100, tinggiPanel));

        panelData.revalidate();
        panelData.repaint();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new CRUD_Karyawan().setVisible(true));
    }
}