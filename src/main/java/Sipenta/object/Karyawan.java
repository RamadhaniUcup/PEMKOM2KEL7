/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.object;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Karyawan {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)

    private String id;
    private String rfid;
    private String nip;
    private String nama;
    private String jabatan;
    // Constructor kosong (WAJIB untuk MongoDB)
    public Karyawan() {
    }

    // Constructor lengkap
    public Karyawan(String rfid, String nip, String nama, String jabatan) {
        this.rfid = rfid;
        this.nip = nip;
        this.nama = nama;
        this.jabatan = jabatan;
    }
    
    @Override
    public String toString() {
    return "Karyawan{" +
            "rfid=" + rfid +
            ", nip=" + nip +
            ", nama=" + nama +
            ", jabatan=" + jabatan + '}';
}
    
    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUid() {
        return rfid;
    }

    public String getNamaLengkap() {
        return nama;
    }

    public void setUid(String uid) {
        this.rfid = uid;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.nama = namaLengkap;
    }
}