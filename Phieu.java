import java.util.*;
import java.io.*;
import java.sql.Date; 

abstract class Phieu implements IFile {
    protected String maPhieu;
    protected java.util.Date ngayLap; 
    protected NhanVienKho nhanVien;
    protected List<SanPham> dsSanPham = new ArrayList<>();

    public Phieu() {
        this.maPhieu = "";
        this.ngayLap = new java.util.Date();
        this.nhanVien = new NhanVienKho();
    }

    public Phieu(String maPhieu, java.util.Date ngayLap, NhanVienKho nv) {
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.nhanVien = nv;
    }

    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }

    @Override
    public void nhap() {
        maPhieu = QuanLyKho.readString("Nhập mã phiếu: ");
        
        ngayLap = QuanLyKho.readDate_Phieu("Nhập ngày lập (yyyy-mm-dd) hoặc enter để dùng hôm nay: ");

        System.out.println("Nhập thông tin nhân viên lập phiếu:");
        nhanVien.nhap(); 
        int n = QuanLyKho.readInt("Nhập số lượng loại sản phẩm trong phiếu: ", 1); 
        dsSanPham.clear();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i+1) + ":");
            int loai = QuanLyKho.readInt("Loại SP: 1. ThucAn 2. NuocUong 3. NonFood: ", 1, 3);
            SanPham sp;
            if (loai == 1) sp = new ThucAn();
            else if (loai == 2) sp = new NuocUong();
            else sp = new NonFood();
            
            sp.nhap(); 
            dsSanPham.add(sp);
        }
    }

    @Override
    public void xuat() {
        System.out.println("\n--- PHIẾU: " + maPhieu + " | Ngày: " + new java.sql.Date(ngayLap.getTime()) + " ---");
        System.out.print("Nhân viên: ");
        nhanVien.xuat();
        System.out.println("Chi tiết sản phẩm:");
        for (SanPham sp : dsSanPham) {
            System.out.print("  - ");
            sp.xuat();
        }
        System.out.println("Tổng tiền: " + tinhTongTien());
    }
    
    public String toFileString() {
        String loaiPhieu = (this instanceof PhieuNhapHang ? "PhieuNhapHang" : "PhieuXuatHang");
        StringBuilder sb = new StringBuilder();
        sb.append(loaiPhieu + "|" + maPhieu + "|" + new java.sql.Date(ngayLap.getTime()) + "\n");      
        sb.append("NV|" + nhanVien.toFileString() + "\n");
        for (SanPham sp : dsSanPham) {
            sb.append("SP|" + sp.toFileString() + "\n");
        }
        sb.append("END_PHIEU\n");
        return sb.toString();
    }

    public abstract double tinhTongTien();
}

class PhieuNhapHang extends Phieu {
    @Override
    public double tinhTongTien() {
        double tong = 0;
        for (SanPham sp : dsSanPham) tong += sp.tinhGiaTri();
        return tong;
    }
}

class PhieuXuatHang extends Phieu {
    @Override
    public double tinhTongTien() {
        double tong = 0;
        for (SanPham sp : dsSanPham) tong += sp.tinhGiaTri() * 1.1; 
        return tong;
    }
}