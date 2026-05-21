package sipenta.dao;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.BsonType;

public class Karyawan {
    
    // 1. TAMBAHAN WAJIB UNTUK MONGODB
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id; // Ini untuk menyimpan _id bawaan MongoDB

    private String rfid;
    private String nip;
    private String nama;
    private String jabatan;

    // Constructor kosong (WAJIB untuk MongoDB)
    public Karyawan() {}

    // Constructor lengkap (tanpa id, karena id dibuat otomatis oleh MongoDB)
    public Karyawan(String rfid, String nip, String nama, String jabatan) {
        this.rfid = rfid;
        this.nip = nip;
        this.nama = nama;
        this.jabatan = jabatan;
    }
    
    // --- GETTER & SETTER STANDAR ---

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

    // --- PERBAIKAN METHOD JEBAKAN ---
    
    // Karena di AdminPage kamu memanggil k.getUid() untuk UID, kita arahkan ke rfid
    public String getUid() {
        return rfid; 
    }

    // Karena di AdminPage kamu memanggil k.getNamaLengkap(), kita arahkan ke nama
    public String getNamaLengkap() {
        return nama;
    }
}