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
        // Mã sẽ được nhập ở lớp con
        ten = QuanLyKho.readString("Nhập tên sản phẩm: ");
        soLuong = QuanLyKho.readInt("Nhập số lượng: ", 1); 
        donGia = QuanLyKho.readDouble("Nhập đơn giá: ", 0.0);
    }

    @Override
    public void xuat() {
        System.out.println("Mã: " + ma + " | Tên: " + ten + " | SL: " + soLuong + " | ĐG: " + donGia);
    }

    public abstract double tinhGiaTri();
    public abstract String toFileString(); 

//tao mot ban sao moi de luu tru san pham 
    public abstract SanPham cloneWithNewQuantity(int newQuantity);
}

class ThucAn extends SanPham {
    protected Date hanSuDung; 
    private String dangBaoBi;
    private static final String[] DANG_BAO_BI_OPTIONS = {"Hop", "Goi"};
    public ThucAn() { 
        super(); 
        dangBaoBi = ""; 
        hanSuDung = new Date(System.currentTimeMillis()); 
    }

    public ThucAn(String ma, String ten, int soLuong, double donGia, Date hanSuDung, String dangBaoBi) {
        super(ma, ten, soLuong, donGia);
        this.hanSuDung = hanSuDung;
        this.dangBaoBi = dangBaoBi;
    }

    public String getDangBaoBi() { return dangBaoBi; }
    public void setDangBaoBi(String dangBaoBi) { this.dangBaoBi = dangBaoBi; }
    public Date getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(Date hanSuDung) { this.hanSuDung = hanSuDung; }


    @Override
    public void nhap() {
        ma = QuanLyKho.readMa("Nhập mã thức ăn (TAXXX): ", "TA"); 
        super.nhap(); 
        java.util.Date hsdUtil = QuanLyKho.readDate_HSD("Nhập hạn sử dụng (yyyy-mm-dd): "); 
        this.hanSuDung = new java.sql.Date(hsdUtil.getTime()); 
        dangBaoBi = QuanLyKho.readStringWithOptions("Nhập dạng bao bì (Hop/Goi): ", DANG_BAO_BI_OPTIONS);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("  HSD: " + hanSuDung + " | Dạng bao bì: " + dangBaoBi);
    }

    @Override
    public double tinhGiaTri() { return soLuong * donGia; }

    @Override
    public String toFileString() {
        return "ThucAn|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + hanSuDung + "|" + dangBaoBi;
    }

    @Override
    public SanPham cloneWithNewQuantity(int newQuantity) {
        return new ThucAn(this.ma, this.ten, newQuantity, this.donGia, this.hanSuDung, this.dangBaoBi);
    }
}

class NuocUong extends SanPham {
    protected Date hanSuDung;
    private String loaiVo;
    private static final String[] LOAI_VO_OPTIONS = {"Lon", "Chai"};

    public NuocUong() { 
        super(); 
        loaiVo = ""; 
        hanSuDung = new Date(System.currentTimeMillis());
    }
    
    public NuocUong(String ma, String ten, int soLuong, double donGia, Date hanSuDung, String loaiVo) {
        super(ma, ten, soLuong, donGia);
        this.hanSuDung = hanSuDung;
        this.loaiVo = loaiVo;
    }

    public String getLoaiVo() { return loaiVo; }
    public void setLoaiVo(String loaiVo) { this.loaiVo = loaiVo; }
    public Date getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(Date hanSuDung) { this.hanSuDung = hanSuDung; }

    @Override
    public void nhap() {
        ma = QuanLyKho.readMa("Nhập mã nước uống (NUXXX): ", "NU"); 
        super.nhap(); 
        java.util.Date hsdUtil = QuanLyKho.readDate_HSD("Nhập hạn sử dụng (yyyy-mm-dd): "); 
        this.hanSuDung = new java.sql.Date(hsdUtil.getTime()); 
        loaiVo = QuanLyKho.readStringWithOptions("Nhập loại vỏ (Lon/Chai): ", LOAI_VO_OPTIONS);    
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("  HSD: " + hanSuDung + " | Loại vỏ: " + loaiVo);
    }

    @Override
    public double tinhGiaTri() { return soLuong * donGia; }

    @Override
    public String toFileString() {
        return "NuocUong|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + hanSuDung + "|" + loaiVo;
    }

//tạo clone 
    @Override
    public SanPham cloneWithNewQuantity(int newQuantity) {
        return new NuocUong(this.ma, this.ten, newQuantity, this.donGia, this.hanSuDung, this.loaiVo);
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
        ma = QuanLyKho.readMa("Nhập mã NonFood (NFXXX): ", "NF"); 
        super.nhap(); 
        chatLieu = QuanLyKho.readString("Nhập chất liệu: ");    
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("  Chất liệu: " + chatLieu);
    }

    @Override
    public double tinhGiaTri() { return soLuong * donGia; }

    @Override
    public String toFileString() {
        return "NonFood|" + ma + "|" + ten + "|" + soLuong + "|" + donGia + "|" + chatLieu;
    }
    
    @Override
    public SanPham cloneWithNewQuantity(int newQuantity) {
        return new NonFood(this.ma, this.ten, newQuantity, this.donGia, this.chatLieu);
    }
}
