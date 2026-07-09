package sipenta.object;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class LogAbsensi {

    @BsonId
    private ObjectId _id; // Wajib ada untuk operasi update/delete di GenericDAO

    @BsonProperty("nip")
    private String nip;

    @BsonProperty("tanggal")
    private String tanggal;

    @BsonProperty("jam_masuk")
    private String jam_masuk;

    @BsonProperty("status")
    private String status;

    public LogAbsensi() {}

    public LogAbsensi(String nip, String tanggal, String jam_masuk, String status) {
        this.nip = nip;
        this.tanggal = tanggal;
        this.jam_masuk = jam_masuk;
        this.status = status;
    }

    // ==========================
    // ID MongoDB
    // ==========================
    // PENTING: getter/setter untuk properti "id" HARUS bertipe ObjectId,
    // sesuai tipe field aslinya (_id). Ini yang dipakai PojoCodec untuk
    // encode/decode dari MongoDB. Kalau tipenya String, PojoCodec akan
    // bentrok saat membaca ObjectId dari database.
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    // Method bantu (bukan getter/setter standar "id") untuk kode lain
    // yang butuh representasi String dari ObjectId, misalnya untuk
    // ditampilkan atau dipakai sebagai parameter filter berbasis String.
    public String getIdHex() {
        return _id != null ? _id.toHexString() : null;
    }

    public void setIdHex(String idHex) {
        if (idHex != null && !idHex.isEmpty()) {
            this._id = new ObjectId(idHex);
        } else {
            this._id = null;
        }
    }

    // GETTER & SETTER
    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getJam_masuk() { return jam_masuk; }
    public void setJam_masuk(String jam_masuk) { this.jam_masuk = jam_masuk; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "LogAbsensi{nip='" + nip + "', tanggal='" + tanggal + "', status='" + status + "'}";
    }
}