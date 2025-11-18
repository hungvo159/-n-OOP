import java.util.*;
import java.io.*;
import java.sql.Date; 

abstract class Phieu implements IFile {
    protected String maPhieu;
    protected java.util.Date ngayLap; 
    protected NhanVienKho nhanVien;
    protected List<SanPham> dsSanPham = new ArrayList<>();
    protected String phanLoai;
    public Phieu() {
        this.maPhieu = "";
        this.ngayLap = new java.util.Date();
        this.nhanVien = new NhanVienKho();
        this.phanLoai = ""; 
    }

    public Phieu(String maPhieu, java.util.Date ngayLap, NhanVienKho nv, String phanLoai) {
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.nhanVien = nv;
        this.phanLoai = phanLoai;
    }

    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }
    public String getPhanLoai() { return phanLoai; }
    public void setPhanLoai(String phanLoai) { this.phanLoai = phanLoai; }
    public List<SanPham> getDsSanPham() { return dsSanPham; }

    @Override
    public void nhap() {
        nhap(null);
    }
    
    public void nhap(String maCoDinh) {
        if (maCoDinh != null) {
            this.maPhieu = maCoDinh; // Giữ mã cũ khi sửa
        } else {
            // Nhập mã mới khi thêm
            String prefix = "PHIEU";
            String prompt = "Nhập mã phiếu (" + prefix + "XXX): ";
            if ("PN".equals(this.phanLoai)) {
                prefix = "PN";
                prompt = "Nhập mã phiếu nhập (PNXXX): ";
            } else if ("PX".equals(this.phanLoai)) {
                prefix = "PX";
                prompt = "Nhập mã phiếu xuất (PXXXX): ";
            }
            maPhieu = QuanLyKho.readMa(prompt, prefix);
        }
        
        ngayLap = QuanLyKho.readDate_Phieu("Nhập ngày lập (yyyy-mm-dd) hoặc enter để dùng hôm nay: ");
    }
    @Override
    public void xuat() {
        System.out.println("\n--- PHIẾU " + phanLoai + ": " + maPhieu + " | Ngày: " + new java.sql.Date(ngayLap.getTime()) + " ---");
        System.out.print("Nhân viên: ");
        if (nhanVien != null && nhanVien.getMaNV() != null && !nhanVien.getMaNV().isEmpty()) {
             nhanVien.xuat();
        } else {
            System.out.println(" (Không có thông tin nhân viên)");
        }

        if (this instanceof PhieuNhapHang) {
            String ncc = ((PhieuNhapHang)this).getNhaCungCap();
            System.out.println("Nhà Cung Cấp: " + (ncc != null ? ncc : "(Chưa có)"));
        } else if (this instanceof PhieuXuatHang) {
            System.out.print("Khách Hàng: ");
            KhachHang kh = ((PhieuXuatHang)this).getKhachHang();
            if (kh != null && kh.getMa() != null && !kh.getMa().isEmpty()) {
                kh.xuat();
            } else {
                System.out.println(" (Không có thông tin khách hàng)");
            }
        }

        System.out.println("Chi tiết sản phẩm:");
        for (SanPham sp : dsSanPham) {
            System.out.print("  - ");
            sp.xuat();
        }
        System.out.println("Tổng tiền: " + tinhTongTien());
    }
    
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(phanLoai + "|" + maPhieu + "|" + new java.sql.Date(ngayLap.getTime()) + "\n");      
        
        if (nhanVien != null && nhanVien.getMaNV() != null && !nhanVien.getMaNV().isEmpty()) {
            sb.append("NV|" + nhanVien.toFileString() + "\n");
        }
        
        if (this instanceof PhieuNhapHang) {
            String ncc = ((PhieuNhapHang)this).getNhaCungCap();
            if (ncc != null && !ncc.isEmpty()) {
                sb.append("NCC|" + ncc + "\n");
            }
        } else if (this instanceof PhieuXuatHang) {
            KhachHang kh = ((PhieuXuatHang)this).getKhachHang();
            if (kh != null && kh.getMa() != null && !kh.getMa().isEmpty()) {
                sb.append("KH|" + kh.toFileString() + "\n");
            }
        }

        for (SanPham sp : dsSanPham) {
            sb.append("SP|" + sp.toFileString() + "\n");
        }
        sb.append("END_PHIEU\n");
        return sb.toString();
    }

    public abstract double tinhTongTien();
}

class PhieuNhapHang extends Phieu {
    protected String nhaCungCap; 

    public PhieuNhapHang(){
        super();
        this.phanLoai = "PN";
        this.nhaCungCap = "";
    }
    
    public String getNhaCungCap() { return nhaCungCap; }
    public void setNhaCungCap(String nhaCungCap) { this.nhaCungCap = nhaCungCap; }

    @Override
    public double tinhTongTien() {
        double tong = 0;
        for (SanPham sp : dsSanPham) tong += sp.tinhGiaTri();
        return tong;
    }
}

class PhieuXuatHang extends Phieu {
    protected KhachHang khachHang; 

     public PhieuXuatHang(){
        super();
        this.phanLoai = "PX";
        this.khachHang = new KhachHang();
    }
    
    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    @Override
    public double tinhTongTien() {
        double tong = 0;
        for (SanPham sp : dsSanPham) tong += sp.tinhGiaTri() * 1.1; 
        return tong;
    }
}
