package sipenta.dao;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Karyawan {

    @BsonId
    private ObjectId id;

    @BsonProperty("uid")
    private String uid;

    @BsonProperty("nip")
    private String nip;

    @BsonProperty("nama")
    private String nama;

    @BsonProperty("jabatan")
    private String jabatan;

    // Constructor kosong (WAJIB untuk MongoDB)
    public Karyawan() {
    }

    // Constructor lengkap
    public Karyawan(String uid, String nip, String nama, String jabatan) {
        this.uid = uid;
        this.nip = nip;
        this.nama = nama;
        this.jabatan = jabatan;
    }

    // ==========================
    // ID MongoDB
    // ==========================
    // PENTING: getter/setter untuk field "id" HARUS bertipe ObjectId,
    // sesuai tipe field aslinya. Ini yang dipakai PojoCodec untuk
    // encode/decode dari MongoDB.
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    // Method bantu (bukan getter/setter standar "id") untuk kode lama
    // yang butuh representasi String dari ObjectId.
    // Nama sengaja dibuat beda (getIdHex/setIdHex) supaya PojoCodec
    // tidak salah kira properti "id" bertipe String.
    public String getIdHex() {
        return id != null ? id.toHexString() : null;
    }

    public void setIdHex(String idHex) {
        if (idHex != null && !idHex.isEmpty()) {
            this.id = new ObjectId(idHex);
        } else {
            this.id = null;
        }
    }

    // ==========================
    // UID RFID
    // ==========================
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Agar kode lama tetap berjalan
    public String getRfid() {
        return uid;
    }

    public void setRfid(String rfid) {
        this.uid = rfid;
    }

    // ==========================
    // NIP / ID Karyawan
    // ==========================
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    // Alias supaya kompatibel dengan kode baru
    public String getIdKaryawan() {
        return nip;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.nip = idKaryawan;
    }

    // ==========================
    // Nama
    // ==========================
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Alias
    public String getNamaLengkap() {
        return nama;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.nama = namaLengkap;
    }

    // ==========================
    // Jabatan
    // ==========================
    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}