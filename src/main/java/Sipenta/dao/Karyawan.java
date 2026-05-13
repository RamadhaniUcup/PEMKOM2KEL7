/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sipenta.dao;

public class Karyawan {
    
    private String rfid;
    private String nip;
    private String nama;
    private String jabatan;

    // Constructor kosong (WAJIB untuk MongoDB)
    public Karyawan() {}

    // Constructor lengkap
    public Karyawan(String rfid, String nip, String nama, String jabatan) {
        this.rfid = rfid;
        this.nip = nip;
        this.nama = nama;
        this.jabatan = jabatan;
    }
    

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}


/**
 *
 * @author VICTUS
 */

