import java.util.*;

abstract class Nguoi implements IFile {
    protected String hoTen;
    protected String ngaySinh;
    protected String gioiTinh;

    public Nguoi() { this.hoTen = ""; this.ngaySinh = ""; this.gioiTinh = ""; }

    public Nguoi(String hoTen, String ngaySinh, String gioiTinh) {
        this.hoTen = hoTen; this.ngaySinh = ngaySinh; this.gioiTinh = gioiTinh;
    }

    public abstract void nhap();
    public abstract void xuat();

    public String getHoTen() { return hoTen; }
    public String getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
}

class KhachHang extends Nguoi {
    private String maKhachHang;
    private String soDienThoai;
    private String diaChi;

    public KhachHang() { super(); this.maKhachHang = ""; this.soDienThoai = ""; this.diaChi = ""; }

    public KhachHang(String ma, String ten, String ns, String gt, String sdt, String dc) {
        super(ten, ns, gt);
        this.maKhachHang = ma;
        this.soDienThoai = sdt;
        this.diaChi = dc;
    }
    
    public String getMa(){ return maKhachHang; }
    public String toFileString() {
        return maKhachHang + "|" + hoTen + "|" + ngaySinh + "|" + gioiTinh + "|" + soDienThoai + "|" + diaChi;
    }

    @Override
    public void nhap() {
        maKhachHang = QuanLyKho.readString("Nhập mã KH: ");
        hoTen = QuanLyKho.readString("Nhập họ tên: ");
        
        java.util.Date nsDate = QuanLyKho.readDate_YYYYMMDD("Nhập ngày sinh (yyyy-mm-dd): ");
        ngaySinh = new java.sql.Date(nsDate.getTime()).toString();

        gioiTinh = QuanLyKho.readString("Nhập giới tính: ");
        soDienThoai = QuanLyKho.readPhoneString("Nhập số điện thoại: "); 
        diaChi = QuanLyKho.readString("Nhập địa chỉ: ");
    }

    @Override
    public void xuat() {
        System.out.println("MãKH: " + maKhachHang + " | Họ tên: " + hoTen + " | NS: " + ngaySinh + " | GT: " + gioiTinh + " | SĐT: " + soDienThoai + " | ĐC: " + diaChi);
    }

    public String getMaKhachHang() { return maKhachHang; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getDiaChi() { return diaChi; }
}

class NhanVienKho extends Nguoi {
    private String maNV;
    private String chucVu;
    private String caTruc;

    public NhanVienKho() { super(); this.maNV = ""; this.chucVu = ""; this.caTruc = ""; }

    public NhanVienKho(String ma, String ten, String ns, String cv, String ca) {
        super(ten, ns, ""); // Giới tính không được nhập ở hàm nhap(), để trống
        this.maNV = ma;
        this.chucVu = cv;
        this.caTruc = ca;
    }

    public String toFileString() {
        return maNV + "|" + hoTen + "|" + ngaySinh + "|" + chucVu + "|" + caTruc;
    }

    @Override
    public void nhap() {
        maNV = QuanLyKho.readString("Nhập mã NV: ");
        hoTen = QuanLyKho.readString("Nhập họ tên: ");

        java.util.Date nsDate = QuanLyKho.readDate_YYYYMMDD("Nhập ngày sinh (yyyy-mm-dd): ");
        ngaySinh = new java.sql.Date(nsDate.getTime()).toString();
        
        chucVu = QuanLyKho.readString("Nhập chức vụ: ");
        caTruc = QuanLyKho.readString("Nhập ca trực: ");
    }

    @Override
    public void xuat() {
        System.out.println("MaNV: " + maNV + " | Họ tên: " + hoTen + " | NS: " + ngaySinh + " | CV: " + chucVu + " | Ca: " + caTruc);
    }

    public String getMaNV() { return maNV; }
    public String getHoTen() { return hoTen; }
    public String getChucVu() { return chucVu; }
    public String getCaTruc() { return caTruc; }
}