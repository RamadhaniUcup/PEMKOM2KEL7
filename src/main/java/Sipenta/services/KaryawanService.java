/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.services;

import Sipenta.object.Karyawan;
import Sipenta.ui.Jframs.Adminpage;
import Sipenta.dao.GenericDAO;
import com.mongodb.client.model.Filters;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class KaryawanService {

    // Variabel dao harus tahu dia membawa objek Karyawan
    private final GenericDAO<Karyawan> dao;

    public KaryawanService() {

        // Panggil dengan Class dan nama koleksi
            this.dao = new GenericDAO<>("karyawan", Karyawan.class);
    }
    public void tambahKaryawan(Karyawan karyawanBaru) {
        dao.save(karyawanBaru);
    }
    
    public List<Karyawan> findAll() {
        return dao.findAll();
    }

    public List<Karyawan> search(String key) {
        Bson filter = Filters.or(
                Filters.regex("nama", key, "i"),
                Filters.regex("nip", key, "i"),
                Filters.regex("jabatan", key, "i")
        );
        
        return dao.findMany(filter);
    }

    
    public void tampilKaryawan(JPanel panelTarget, String key) {
        List<Karyawan> daftarKaryawan;

        if (key == null || key.isEmpty()) {
            daftarKaryawan = dao.findAll();
        } else {
            daftarKaryawan = search(key);
        }
        
        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        for (Karyawan k : daftarKaryawan) {
            JPanel card = new JPanel();
            card.setLayout(new GridLayout(5, 1, 0, 5));
            card.setPreferredSize(new java.awt.Dimension(300, 150));
            card.setBorder(javax.swing.BorderFactory.createEmptyBorder(
                10, 10, 10, 10));
            
            JLabel lblNama = new JLabel("Nama : " + k.getNama());
            JLabel lblID = new JLabel("ID Karyawan : " + k.getId());
            JLabel lblNip = new JLabel("NIP : " + k.getNip());
            JLabel lblJabatan = new JLabel("Jabatan : " + k.getJabatan());
            
            JPanel tombolPanel = new JPanel(new GridLayout(1, 2, 5, 0));
            
            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Delete");
            
            btnEdit.addActionListener((ActionEvent e) -> {
                Adminpage.idKaryawanEdit = k.getId();
                Adminpage.txtUID.setText(k.getRfid());
                Adminpage.txtID.setText(k.getNip());
                Adminpage.txtNama.setText(k.getNama());
                Adminpage.cbDepartemen.setSelectedItem(k.getJabatan());
            });
            
            btnDelete.addActionListener((ActionEvent e) -> {
                deleteKaryawan(k.getId());
            });
        
            tombolPanel.add(btnEdit);
            tombolPanel.add(btnDelete);

            card.add(lblNama);
            card.add(lblID);
            card.add(lblNip);
            card.add(lblJabatan);
            card.add(tombolPanel);

            gridPanel.add(card);
        }
        
        panelTarget.add(gridPanel, BorderLayout.NORTH);

        panelTarget.revalidate();
        panelTarget.repaint();
    }
    
    
    public void updateKaryawan(Karyawan k) {
        Bson filter =
                Filters.eq("_id",
                        new ObjectId(k.getId()));

        dao.update(filter, k);
    }
  
    public void deleteKaryawan(String id) {

        Bson filter =
                Filters.eq("_id",
                        new ObjectId(id));

        dao.delete(filter);
    }
}