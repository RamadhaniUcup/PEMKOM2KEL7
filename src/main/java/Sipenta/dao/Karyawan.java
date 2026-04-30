/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.dao;

public class Karyawan {
    
    private String uiRfid;
    private String idKaryawan;
    private String namaLengkap;
    private String jabatan;

    public Karyawan(String uiRfid, String idKaryawan, String namaLengkap, String jabatan) {
        this.uiRfid = uiRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.jabatan = jabatan;
    }

    public String getUiRfid() {
        return uiRfid;
    }

    public void setUiRfid(String uiRfid) {
        this.uiRfid = uiRfid;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
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

