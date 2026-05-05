package sipenta.object;

public class LogAbsensi {

    private String nip;
    private String tanggal;
    private String jam_masuk;
    private String status;

    // Constructor kosong (WAJIB untuk MongoDB)
    public LogAbsensi() {}

    // Constructor lengkap
    public LogAbsensi(String nip, String tanggal, String jam_masuk, String status) {
        this.nip = nip;
        this.tanggal = tanggal;
        this.jam_masuk = jam_masuk;
        this.status = status;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}