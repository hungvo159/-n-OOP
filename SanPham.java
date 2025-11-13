import java.util.*;
import java.sql.Date; 

abstract class SanPham implements IFile {
    protected String ma;
    protected String ten;
    protected int soLuong;
    protected double donGia;

    public SanPham() {
        this.ma = "";
        this.ten = "";
        this.soLuong = 0;
        this.donGia = 0;
    }

    public SanPham(String ma, String ten, int soLuong, double donGia) {
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    @Override
    public void nhap() {
        ma = QuanLyKho.readString("Nhập mã sản phẩm: ");
        ten = QuanLyKho.readString("Nhập tên sản phẩm: ");
        soLuong = QuanLyKho.readInt("Nhập số lượng: ", 0);
        donGia = QuanLyKho.readDouble("Nhập đơn giá: ", 0.0);
    }

    @Override
    public void xuat() {
        System.out.println("Mã: " + ma + " | Tên: " + ten + " | SL: " + soLuong + " | ĐG: " + donGia);
    }

    public abstract double tinhGiaTri();
    public abstract String toFileString(); 
}

class Food extends SanPham {
    protected Date hanSuDung;

    public Food() { super(); hanSuDung = new Date(System.currentTimeMillis()); }

    public Food(String ma, String ten, int soLuong, double donGia, Date hanSuDung) {
        super(ma, ten, soLuong, donGia);
        this.hanSuDung = hanSuDung;
    }

    public Date getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(Date hanSuDung) { this.hanSuDung = hanSuDung; }

    @Override
    public void nhap() {
        super.nhap();
        java.util.Date hsdUtil = QuanLyKho.readDate_YYYYMMDD("Nhập hạn sử dụng (yyyy-mm-dd): ");
        this.hanSuDung = new java.sql.Date(hsdUtil.getTime()); 
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("HSD: " + hanSuDung);
    }

    @Override
    public double tinhGiaTri() { return soLuong * donGia; }
    
    @Override
    public String toFileString() { return ""; }
}

class ThucAn extends Food {
    private String dangBaoBi;
    private static final String[] DANG_BAO_BI_OPTIONS = {"Hop", "Goi"};

    public ThucAn() { super(); dangBaoBi = ""; }

    public ThucAn(String ma, String ten, int soLuong, double donGia, Date hanSuDung, String dangBaoBi) {
        super(ma, ten, soLuong, donGia, hanSuDung);
        this.dangBaoBi = dangBaoBi;
    }

    public String getDangBaoBi() { return dangBaoBi; }
    public void setDangBaoBi(String dangBaoBi) { this.dangBaoBi = dangBaoBi; }

    @Override
    public void nhap() {
        super.nhap();
        dangBaoBi = QuanLyKho.readStringWithOptions("Nhập dạng bao bì (Hop/Goi): ", DANG_BAO_BI_OPTIONS);    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Dạng bao bì: " + dangBaoBi);
    }

    @Override
    public String toFileString() {
        return "ThucAn|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + hanSuDung + "|" + dangBaoBi;
    }
}

class NuocUong extends Food {
    private String loaiVo;
    private static final String[] LOAI_VO_OPTIONS = {"Lon", "Chai"};
    public NuocUong() { super(); loaiVo = ""; }
    public NuocUong(String ma, String ten, int soLuong, double donGia, Date hanSuDung, String loaiVo) {
        super(ma, ten, soLuong, donGia, hanSuDung);
        this.loaiVo = loaiVo;
    }

    public String getLoaiVo() { return loaiVo; }
    public void setLoaiVo(String loaiVo) { this.loaiVo = loaiVo; }

    @Override
    public void nhap() {
        super.nhap();
         loaiVo = QuanLyKho.readStringWithOptions("Nhập loại vỏ (Lon/Chai): ", LOAI_VO_OPTIONS);    
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Loại vỏ: " + loaiVo);
    }

    @Override
    public String toFileString() {
        return "NuocUong|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + hanSuDung + "|" + loaiVo;
    }
}

class NonFood extends SanPham {
    private String chatLieu;
    public NonFood() { super(); chatLieu = ""; }
    public NonFood(String ma, String ten, int soLuong, double donGia, String chatLieu) {
        super(ma, ten, soLuong, donGia);
        this.chatLieu = chatLieu;
    }

    public String getChatLieu() { return chatLieu; }
    public void setChatLieu(String chatLieu) { this.chatLieu = chatLieu; }

    @Override
    public void nhap() {
        super.nhap();
        chatLieu = QuanLyKho.readString("Nhập chất liệu: ");    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Chất liệu: " + chatLieu);
    }

    @Override
    public double tinhGiaTri() { return soLuong * donGia; }

    @Override
    public String toFileString() {
        return "NonFood|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + chatLieu;
    }
}