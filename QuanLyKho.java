import java.io.*;
import java.util.*;
import java.sql.Date; 
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Calendar;

public class QuanLyKho {
    public static final Scanner SC = new Scanner(System.in);

    private QuanLyPhieu qlPhieu;
    private QuanLyNhanVien qlNhanVien;
    private QuanLySanPham qlSanPham;
    private QuanLyKhachHang qlKhachHang;

    public QuanLyKho() {
        qlNhanVien = new QuanLyNhanVien();
        qlKhachHang = new QuanLyKhachHang();
        qlSanPham = new QuanLySanPham();
        qlPhieu = new QuanLyPhieu(qlSanPham, qlNhanVien, qlKhachHang); 
    }
    
    public void hienMenu() {
        if (!dangNhap()) {
            System.out.println("Sai tài khoản hoặc mật khẩu. Thoát chương trình.");
            return;
        }
        int chon;
        do {
            System.out.println("\n==== MENU CHÍNH ====");
            System.out.println("1. Quản lý phiếu");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý sản phẩm");
            System.out.println("4. Quản lý khách hàng");
            System.out.println("5. Thoát");
            System.out.print("");
            chon = readInt("Chọn: ", 1, 5);
            switch (chon) {
                case 1 -> qlPhieu.menuPhieu();
                case 2 -> qlNhanVien.menuNhanVien();
                case 3 -> qlSanPham.menuSanPham();
                case 4 -> qlKhachHang.menuKhachHang();
                case 5 -> System.out.println("Thoát chương trình");
            }
        } while (chon != 5);
    }

    protected boolean dangNhap() {
        String userDung = "admin";
        String passDung = "123";
        System.out.println("==== ĐĂNG NHẬP HỆ THỐNG ====");
        System.out.print("Tài khoản: ");
        String user = SC.nextLine();
        System.out.print("Mật khẩu: ");
        String pass = SC.nextLine();
        return user.equals(userDung) && pass.equals(passDung);
    }

    
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Nhập số không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            int val = readInt(prompt);
            if (val >= min && val <= max) return val;
            System.out.println("Lựa chọn phải nằm trong khoảng [" + min + ", " + max + "]. Vui lòng nhập lại.");
        }
    }
    
    public static int readInt(String prompt, int min) {
        while (true) {
            int val = readInt(prompt);
            if (val >= min) return val;
            System.out.println("Giá trị phải lớn hơn hoặc bằng " + min + ". Vui lòng nhập lại.");
        }
    }
    public static double readDouble(String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(SC.nextLine().trim());
                if (val >= min) return val;
                System.out.println("Giá trị phải lớn hơn hoặc bằng " + min + ". Vui lòng nhập lại.");
            } catch (Exception e) {
                System.out.println("Nhập số thập phân không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

    public static String readString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            if (!allowEmpty && s.isEmpty()) {
                System.out.println("Không được để trống. Vui lòng nhập lại.");
            } else {
                return s;
            }
        }
    }

    public static String readString(String prompt) {
        return readString(prompt, false);
    }

    public static java.util.Date readDate_YYYYMMDD(String prompt) {
        while (true) {
            String s = readString(prompt); 
            try {
                java.sql.Date sqlDate = java.sql.Date.valueOf(s);
                return new java.util.Date(sqlDate.getTime());
            } catch (IllegalArgumentException e) {
                System.out.println("Định dạng ngày không hợp lệ (phải là yyyy-mm-dd). Vui lòng nhập lại.");
            }
        }
    }
    
    public static java.util.Date readDate_Phieu(String prompt) {
        while (true) {
            String s = readString(prompt, true); 
            if (s.isEmpty()) {
                return new java.util.Date(); 
            }
            try {
                java.sql.Date sqlDate = java.sql.Date.valueOf(s);
                return new java.util.Date(sqlDate.getTime());
            } catch (IllegalArgumentException e) {
                System.out.println("Định dạng ngày không hợp lệ (phải là yyyy-mm-dd). Vui lòng nhập lại.");
            }
        }
    }
    public static String readStringWithOptions(String prompt, String[] options) {
        while (true) {
            String s = readString(prompt); 
            for (String opt : options) {
                if (s.equalsIgnoreCase(opt)) {
                    return opt; 
                }
            }
            System.out.println("Lựa chọn không hợp lệ. Phải là một trong: " + Arrays.toString(options));
        }
    }
    
    public static String readPhoneString(String prompt) {
        final Pattern PHONE_PATTERN = Pattern.compile("^\\d+$"); 
        while (true) {
            String s = readString(prompt); 
            if (PHONE_PATTERN.matcher(s).matches()) {
                return s;
            }
            System.out.println("Số điện thoại không hợp lệ. Chỉ được phép nhập số (0-9).");
        }
    }
    
    private static java.util.Date clearTime(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static java.util.Date readDate_HSD(String prompt) {
        while (true) {
            java.util.Date hsd = readDate_YYYYMMDD(prompt);
            java.util.Date homNay = new java.util.Date();
            if (clearTime(hsd).after(clearTime(homNay))) {
                return hsd;
            }
            System.out.println("Hạn sử dụng phải lớn hơn ngày hiện tại. Vui lòng nhập lại.");
        }
    }
    //kiem tra ma cho toan bo 
    public static String readMa(String prompt, String prefix) {
        final Pattern MA_PATTERN = Pattern.compile("^(TA|NU|NF)\\d{3}$");
        final Pattern PREFIX_PATTERN = Pattern.compile("^" + prefix + "\\d{3}$");

        while (true) {
            String s = readString(prompt);
            if (prefix.equals("SP")) { 
                if (MA_PATTERN.matcher(s).matches()) {
                    return s;
                }
                System.out.println("Mã không hợp lệ. Phải là TA/NU/NF + 3 chữ số (ví dụ: TA001).");
            } 
            else {
                if (PREFIX_PATTERN.matcher(s).matches()) {
                    return s;
                }
                System.out.println("Mã không hợp lệ. Phải có dạng " + prefix + "XXX (XXX là 3 chữ số).");
            }
        }
    }
    
    public static void main(String[] args) {
        new QuanLyKho().hienMenu();
    }
}

class QuanLyPhieu {
    private List<Phieu> danhSachPhieu = new ArrayList<>();
    private final String FILE_PHIEU_NHAP = "phieunhap.txt";
    private final String FILE_PHIEU_XUAT = "phieuxuat.txt";
    
    private QuanLySanPham qlSanPham;
    private QuanLyNhanVien qlNhanVien;
    private QuanLyKhachHang qlKhachHang; 

    public QuanLyPhieu(QuanLySanPham qlSanPham, QuanLyNhanVien qlNhanVien, QuanLyKhachHang qlKhachHang) {
        this.qlSanPham = qlSanPham;
        this.qlNhanVien = qlNhanVien; 
        this.qlKhachHang = qlKhachHang; 
        this.danhSachPhieu = new ArrayList<>();
        docFile(FILE_PHIEU_NHAP, "PN"); 
        docFile(FILE_PHIEU_XUAT, "PX"); 
    }
    
     public void menuPhieu() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ PHIẾU ====");
            System.out.println("1. Nhập phiếu nhập hàng");
            System.out.println("2. Nhập phiếu xuất hàng");
            System.out.println("3. Xem tất cả phiếu");
            System.out.println("4. Tìm kiếm phiếu");
            System.out.println("5. Xóa phiếu (Cảnh báo: Không hoàn tác tồn kho)");
            System.out.println("6. Sửa phiếu (Bao gồm hoàn tác tồn kho)");
            System.out.println("7. Thống kê");
            System.out.println("8. Quay lại menu chính");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 8);            
            switch (chon) {
                case 1 -> nhapPhieuNhap();
                case 2 -> nhapPhieuXuat();
                case 3 -> xemTatCa();
                case 4 -> timKiem();
                case 5 -> xoaPhieu();
                case 6 -> suaPhieu(); 
                case 7 -> thongKe();
                case 8 -> {}
            }
        } while (chon != 8);
    }
    
    private NhanVienKho xacThucNhanVien() {
        System.out.println("Chọn nhân viên lập phiếu:");
        NhanVienKho nv = null;
        while (nv == null) {
            String maNV = QuanLyKho.readMa("Nhập mã nhân viên (NVXXX): ", "NV");
            nv = this.qlNhanVien.timNhanVienTheoMa(maNV); 
            
            if (nv == null) {
                System.out.println("Lỗi: Không tìm thấy nhân viên. Vui lòng nhập lại.");
            } else {
                System.out.print("Đã chọn nhân viên: ");
                nv.xuat();
            }
        }
        return nv;
    }

    private KhachHang xacThucKhachHang() {
        System.out.println("Chọn khách hàng nhận hàng:");
        KhachHang kh = null;
        while (kh == null) {
            String maKH = QuanLyKho.readMa("Nhập mã khách hàng (KHXXX): ", "KH");
            kh = this.qlKhachHang.timKhachHangTheoMa(maKH);
            
            if (kh == null) {
                System.out.println("Lỗi: Không tìm thấy khách hàng. Vui lòng nhập lại.");
            } else {
                System.out.print("Đã chọn khách hàng: ");
                kh.xuat();
            }
        }
        return kh;
    }
    
    private Phieu timPhieuTheoMa(String ma) {
        return danhSachPhieu.stream()
            .filter(p -> p.getMaPhieu().equalsIgnoreCase(ma))
            .findFirst()
            .orElse(null);
    }

    private List<SanPham> nhapDanhSachSanPhamChoPhieu() {
        System.out.println("--- Nhập chi tiết sản phẩm cho phiếu ---");
        List<SanPham> dsKetQua = new ArrayList<>();
        int n = QuanLyKho.readInt("Nhập số lượng sản phẩm trong phiếu: ", 1);
        
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i+1) + ":");
            SanPham spKho = null;
            while (spKho == null) {
                String maSP = QuanLyKho.readMa("Nhập mã sản phẩm (TA/NU/NF + XXX): ", "SP");
                spKho = this.qlSanPham.timSanPhamTheoMa(maSP);
                
                if (spKho == null) {
                    System.out.println("Lỗi: Không tìm thấy sản phẩm có mã. Vui lòng nhập lại.");
                }
            }
            System.out.println("Đã tìm thấy sản phẩm trong kho:");
            spKho.xuat(); 
          
            int soLuongPhieu = QuanLyKho.readInt("Nhập số lượng: ", 1); 
            
            // tạo thêm clone cho phiếu, giúp tránh hỏng dữ liệu 
            SanPham spPhieu = spKho.cloneWithNewQuantity(soLuongPhieu);
            dsKetQua.add(spPhieu);
            System.out.println("Đã thêm " + soLuongPhieu + " " + spPhieu.getTen() + " vào phiếu.");
        }
        return dsKetQua;
    }
    
    private PhieuNhapHang nhapChiTietPhieuNhap(String maPhieuCoDinh) {
        NhanVienKho nv = xacThucNhanVien();
        String ncc = QuanLyKho.readString("Nhập tên nhà cung cấp: ");
        
        PhieuNhapHang p = new PhieuNhapHang();
        p.nhanVien = nv;
        p.setNhaCungCap(ncc);
        p.setPhanLoai("PN");
        p.nhap(maPhieuCoDinh); 
        
        // Nhập chi tiết SP 
        List<SanPham> dsSPMoi = nhapDanhSachSanPhamChoPhieu();
        if (dsSPMoi.isEmpty()) {
             System.out.println("Phiếu không có sản phẩm. Hủy tạo phiếu.");
             return null;
        }
        p.dsSanPham = dsSPMoi; 
   
        // Kiểm tra mã trùng 
        if (maPhieuCoDinh == null && timPhieuTheoMa(p.getMaPhieu()) != null) {
            System.out.println("Lỗi: Mã phiếu " + p.getMaPhieu() + " đã tồn tại.");
            return null;
        }
        return p;
    }

    private PhieuXuatHang nhapChiTietPhieuXuat(String maPhieuCoDinh) {
        NhanVienKho nv = xacThucNhanVien();
        KhachHang kh = xacThucKhachHang(); 
        PhieuXuatHang p = new PhieuXuatHang();
        p.nhanVien = nv;
        p.setKhachHang(kh);
        p.setPhanLoai("PX");
        p.nhap(maPhieuCoDinh); 
        
        //Nhập chi tiết SP 
        List<SanPham> dsSPMoi = nhapDanhSachSanPhamChoPhieu();
        if (dsSPMoi.isEmpty()) {
             System.out.println("Phiếu không có sản phẩm. Hủy tạo phiếu.");
             return null;
        }
        p.dsSanPham = dsSPMoi; // Gán ds sản phẩm vào phiếu
        
        // Kiểm tra mã trùng 
        if (maPhieuCoDinh == null && timPhieuTheoMa(p.getMaPhieu()) != null) {
            System.out.println("Lỗi: Mã phiếu " + p.getMaPhieu() + " đã tồn tại.");
            return null;
        }
        return p;
    }

    private void nhapPhieuNhap() {
        System.out.println("--- LẬP PHIẾU NHẬP HÀNG ---");
        PhieuNhapHang p = nhapChiTietPhieuNhap(null); 
        
        if (p == null) {
            System.out.println("Thêm phiếu nhập thất bại.");
            return;
        }

        if (qlSanPham.capNhatSoLuong(p.getDsSanPham(), "PN")) {
            danhSachPhieu.add(p);
            luuFile(FILE_PHIEU_NHAP, "PN"); 
            System.out.println("Đã thêm phiếu nhập và cập nhật tồn kho.");
        } else {
            System.out.println("Lỗi khi cập nhật kho. Không lưu phiếu.");
        }
    }

    private void nhapPhieuXuat() {
        System.out.println("--- LẬP PHIẾU XUẤT HÀNG ---");
        PhieuXuatHang p = nhapChiTietPhieuXuat(null); 

        if (p == null) {
            System.out.println("Thêm phiếu xuất thất bại.");
            return;
        }
        
        if (qlSanPham.capNhatSoLuong(p.getDsSanPham(), "PX")) {
            danhSachPhieu.add(p);
            luuFile(FILE_PHIEU_XUAT, "PX"); 
            System.out.println("Đã thêm phiếu xuất và cập nhật tồn kho.");
        } else {
            System.out.println("Lỗi khi cập nhật kho (có thể không đủ hàng). Không lưu phiếu.");
        }
    }

    private void xemTatCa() {
        if (danhSachPhieu.isEmpty()) System.out.println("Danh sách rỗng!");
        else for (Phieu p : danhSachPhieu) p.xuat();
    }

    private void luuFile(String tenFile, String loaiPhieu) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (Phieu p : danhSachPhieu) {
                if (p.getPhanLoai().equals(loaiPhieu)) {
                    bw.write(p.toFileString()); 
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file phiếu ("+loaiPhieu+"): " + e.getMessage());
        }
    }

    private void docFile(String tenFile, String loaiPhieuExpected) {
        File f = new File(tenFile);
        if (!f.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            String line;
            Phieu p = null;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\|");
                if (t.length == 0) continue;

                String type = t[0];
                if (type.equals(loaiPhieuExpected)) { 
                    p = type.equals("PN") ? new PhieuNhapHang() : new PhieuXuatHang();
                    p.setPhanLoai(type); 
                    p.setMaPhieu(t[1]);
                    try { p.ngayLap = java.sql.Date.valueOf(t[2]); } catch (Exception e) { p.ngayLap = new java.util.Date(); }
                    p.dsSanPham.clear(); 
                } else if (type.equals("NV") && p != null) {
                    if(t.length >= 5) {
                         p.nhanVien = new NhanVienKho(t[1], t[2], t[3], t[4]);
                    }
                } else if (type.equals("NCC") && p instanceof PhieuNhapHang) {
                    if (t.length >= 2) {
                        ((PhieuNhapHang)p).setNhaCungCap(t[1]);
                    }
                } else if (type.equals("KH") && p instanceof PhieuXuatHang) {
                    if (t.length >= 7) {
                        KhachHang kh = new KhachHang(t[1], t[2], t[3], t[4], t[5], t[6]);
                        ((PhieuXuatHang)p).setKhachHang(kh);
                    }
                } else if (type.equals("SP") && p != null) {
                    SanPham sp = null;
                    try {
                        if (t[1].equals("ThucAn") && t.length >= 8) {
                            sp = new ThucAn(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), java.sql.Date.valueOf(t[6]), t[7]);
                        } else if (t[1].equals("NuocUong") && t.length >= 8) {
                            sp = new NuocUong(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), java.sql.Date.valueOf(t[6]), t[7]);
                        } else if (t[1].equals("NonFood") && t.length >= 7) {
                            sp = new NonFood(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), t[6]);
                        }
                        if (sp != null) p.dsSanPham.add(sp);
                    } catch (Exception e) { System.out.println("Lỗi parse dòng SP trong Phieu: " + line); }
                } else if (type.equals("END_PHIEU")) {
                    if (p != null && p.getPhanLoai().equals(loaiPhieuExpected)) { 
                        danhSachPhieu.add(p);
                        p = null; 
                    }
                }
            }
            System.out.println("Đã đọc file " + tenFile + " thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file " + tenFile + ": " + e.getMessage());
        }
    }

    private void timKiem() {
        String ma = QuanLyKho.readString("Nhập mã phiếu cần tìm (PNXXX hoặc PXXXX): "); 
        Phieu p = timPhieuTheoMa(ma);
        if (p != null) {
            p.xuat();
        } else {
            System.out.println("Không tìm thấy phiếu!");
        }
    }
    
    private void xoaPhieu() {
        String ma = QuanLyKho.readString("Nhập mã phiếu cần xóa: "); 
        Phieu pXoa = timPhieuTheoMa(ma);

        if (pXoa != null) {
            danhSachPhieu.remove(pXoa);
            System.out.println("Đã xóa phiếu " + ma + ". (Cảnh báo: Tồn kho KHÔNG được hoàn tác.)");
            if (pXoa.getPhanLoai().equals("PN")) {
                luuFile(FILE_PHIEU_NHAP, "PN");
            } else {
                luuFile(FILE_PHIEU_XUAT, "PX");
            }
        } else {
            System.out.println("Không tìm thấy phiếu " + ma);
        }
    }

    private void suaPhieu() {
        String ma = QuanLyKho.readString("Nhập mã phiếu cần sửa: "); 
        Phieu phieuCu = timPhieuTheoMa(ma);
        
        if (phieuCu == null) {
            System.out.println("Không tìm thấy phiếu " + ma);
            return;
        }

        System.out.println("Đang sửa phiếu. Tồn kho cũ sẽ được hoàn tác.");
        
        if (!qlSanPham.hoanTacKho(phieuCu)) {
            System.out.println("Lỗi: Không thể hoàn tác kho. Hủy bỏ sửa phiếu.");
            return;
        }
        System.out.println("Đã hoàn tác kho tạm thời.");
        System.out.println("--- Vui lòng nhập thông tin mới cho phiếu ---");
        Phieu phieuMoi = null;
        if (phieuCu instanceof PhieuNhapHang) {
            phieuMoi = nhapChiTietPhieuNhap(ma); 
        } else if (phieuCu instanceof PhieuXuatHang) {
            phieuMoi = nhapChiTietPhieuXuat(ma); 
        }

        if (phieuMoi == null) {
            System.out.println("Quá trình nhập mới bị hủy. Đang khôi phục kho...");
            qlSanPham.capNhatSoLuong(phieuCu.getDsSanPham(), phieuCu.getPhanLoai());
            qlSanPham.luuFile();
            System.out.println("Đã khôi phục kho. Phiếu chưa được sửa.");
            return;
        }

        if (qlSanPham.capNhatSoLuong(phieuMoi.getDsSanPham(), phieuMoi.getPhanLoai())) {
            danhSachPhieu.remove(phieuCu); 
            danhSachPhieu.add(phieuMoi);   
            if (phieuMoi.getPhanLoai().equals("PN")) {
                luuFile(FILE_PHIEU_NHAP, "PN");
            } else {
                luuFile(FILE_PHIEU_XUAT, "PX");
            }
            System.out.println("Đã sửa phiếu " + ma + " và cập nhật tồn kho thành công.");
        } else {
            System.out.println("Lỗi: Không thể cập nhật kho với thông tin mới (ví dụ: không đủ hàng).");
            System.out.println("Đang khôi phục kho về trạng thái phiếu cũ...");
            
            qlSanPham.capNhatSoLuong(phieuCu.getDsSanPham(), phieuCu.getPhanLoai());
            qlSanPham.luuFile();
            System.out.println("Đã khôi phục kho. Phiếu chưa được sửa.");
        }
    }

    private void thongKe() {
        long soNhap = danhSachPhieu.stream().filter(p -> p.getPhanLoai().equals("PN")).count();
        long soXuat = danhSachPhieu.stream().filter(p -> p.getPhanLoai().equals("PX")).count();
        double tongNhap = 0, tongXuat = 0;
        for (Phieu p : danhSachPhieu) {
            if (p.getPhanLoai().equals("PN")) tongNhap += p.tinhTongTien();
            else if (p.getPhanLoai().equals("PX")) tongXuat += p.tinhTongTien();
        }
        System.out.println("\n===== THỐNG KÊ =====");
        System.out.println("Phiếu nhập: " + soNhap + " | Tổng tiền nhập: " + tongNhap);
        System.out.println("Phiếu xuất: " + soXuat + " | Tổng tiền xuất: " + tongXuat);
    }
}



class QuanLyNhanVien {
    private List<NhanVienKho> dsNhanVien = new ArrayList<>();
    private final String FILE_NAME = "nhanvien.txt";

    public QuanLyNhanVien() { docFile(FILE_NAME); }
    
    public NhanVienKho timNhanVienTheoMa(String ma) {
        for (NhanVienKho nv : dsNhanVien) {
            if (nv.getMaNV().equalsIgnoreCase(ma)) {
                return nv; 
            }
        }
        return null; 
    }

    public void menuNhanVien() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ NHÂN VIÊN ====");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Xóa nhân viên");
            System.out.println("4. Tìm kiếm");
            System.out.println("5. Quay lại");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 5);
            switch (chon) {
                case 1 -> {
                    NhanVienKho nv = new NhanVienKho();
                    nv.nhap();
                    if(timNhanVienTheoMa(nv.getMaNV()) != null) { 
                        System.out.println("Lỗi: Mã nhân viên " + nv.getMaNV() + " đã tồn tại.");
                    } else {
                        dsNhanVien.add(nv);
                        luuFile(FILE_NAME);
                        System.out.println("Đã thêm nhân viên.");
                    }
                }
                case 2 -> { if (dsNhanVien.isEmpty()) System.out.println("Danh sách trống!"); else dsNhanVien.forEach(NhanVienKho::xuat); }
                case 3 -> {
                    String ma = QuanLyKho.readString("Nhập mã nhân viên cần xóa (NVXXX): "); 
                    boolean ok = dsNhanVien.removeIf(n -> n.getMaNV().equalsIgnoreCase(ma));
                    System.out.println(ok ? "Đã xóa" : "Không tìm thấy");
                    if (ok) luuFile(FILE_NAME);
                }
                case 4 -> {
                    String ma = QuanLyKho.readString("Nhập mã nhân viên cần tìm (NVXXX): ");
                    NhanVienKho nv = timNhanVienTheoMa(ma); 
                    if (nv != null) {
                        nv.xuat();
                    } else {
                        System.out.println("Không tìm thấy nhân viên có mã: " + ma);
                    }
                }
                case 5 -> {}
            }
        } while (chon != 5);
    }

    private void luuFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (NhanVienKho nv : dsNhanVien)
                bw.write(nv.toFileString() + "\n");
        } catch (IOException e) { System.out.println("Lỗi ghi file " + tenFile); }
    }
    
    private void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            dsNhanVien.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\|");
                if (t.length >= 4) { 
                    NhanVienKho nv = new NhanVienKho(t[0], t[1], t[2], t[3]);
                    dsNhanVien.add(nv);
                }
            }
            System.out.println("Đã đọc file " + tenFile + " thành công!");
        } catch (IOException e) { System.out.println("Lỗi đọc file " + tenFile); }
    }
}

class QuanLySanPham {
    private List<SanPham> dsSanPham = new ArrayList<>();
    private final String FILE_NAME = "sanpham.txt";

    public QuanLySanPham() { docFile(FILE_NAME); }
    
    public SanPham timSanPhamTheoMa(String ma) {
        return dsSanPham.stream()
                .filter(sp -> sp.getMa().equalsIgnoreCase(ma))
                .findFirst()
                .orElse(null);
    }

    public boolean hoanTacKho(Phieu phieu) {
        String loaiPhieu = phieu.getPhanLoai();
        
        for (SanPham spPhieu : phieu.getDsSanPham()) {
            SanPham spKho = timSanPhamTheoMa(spPhieu.getMa());
            if (spKho == null) {
                System.out.println("Cảnh báo: Sản phẩm " + spPhieu.getMa() + " trong phiếu cũ không còn tồn tại trong kho.");
                if (loaiPhieu.equals("PN")) {
                    System.out.println("Lỗi nghiêm trọng: Không thể hoàn tác phiếu nhập cho SP đã bị xóa.");
                    return false;
                }
                continue; 
            }

            if (loaiPhieu.equals("PN")) {
                int slMoi = spKho.getSoLuong() - spPhieu.getSoLuong();
                if (slMoi < 0) {
                     System.out.println("Cảnh báo: Hoàn tác phiếu nhập " + spKho.getMa() + " khiến kho bị âm.");
                }
                spKho.setSoLuong(slMoi);
            } else if (loaiPhieu.equals("PX")) {
                spKho.setSoLuong(spKho.getSoLuong() + spPhieu.getSoLuong());
            }
        }
        luuFile(); 
        return true;
    }

    public boolean capNhatSoLuong(List<SanPham> dsSanPhamPhieu, String loaiPhieu) {
        if (loaiPhieu.equals("PX")) {
            for (SanPham spPhieu : dsSanPhamPhieu) {
                SanPham spKho = timSanPhamTheoMa(spPhieu.getMa());
                if (spKho == null) {
                    System.out.println("Lỗi: Sản phẩm " + spPhieu.getMa() + " (" + spPhieu.getTen() + ") không tồn tại trong kho.");
                    return false; 
                }
                if (spKho.getSoLuong() < spPhieu.getSoLuong()) {
                    System.out.println("Lỗi: Sản phẩm " + spPhieu.getMa() + " (" + spPhieu.getTen() + ") không đủ tồn kho.");
                    System.out.println("  (Cần: " + spPhieu.getSoLuong() + " | Có: " + spKho.getSoLuong() + ")");
                    return false; 
                }
            }
        }

        for (SanPham spPhieu : dsSanPhamPhieu) {
            SanPham spKho = timSanPhamTheoMa(spPhieu.getMa());
            if (spKho != null) {
                if (loaiPhieu.equals("PN")) {
                    spKho.setSoLuong(spKho.getSoLuong() + spPhieu.getSoLuong());
                } else if (loaiPhieu.equals("PX")) {
                    spKho.setSoLuong(spKho.getSoLuong() - spPhieu.getSoLuong());
                }
            } else {
                if (loaiPhieu.equals("PN")) {
                    System.out.println("Sản phẩm " + spPhieu.getMa() + " (" + spPhieu.getTen() + ") không có trong kho, tự động thêm mới.");
                    dsSanPham.add(spPhieu); 
                }
            }
        }
        
        luuFile(); 
        return true; 
    }

    public void menuSanPham() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ SẢN PHẨM ====");
            System.out.println("1. Thêm sản phẩm vào kho (không qua phiếu)");
            System.out.println("2. Xem danh sách sản phẩm trong kho");
            System.out.println("3. Tìm/Xóa/Sửa sản phẩm");
            System.out.println("4. Quay lại");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 4);
            switch (chon) {
                case 1 -> { nhapSanPham(); } 
                case 2 -> { if (dsSanPham.isEmpty()) System.out.println("Danh sách trống!"); else dsSanPham.forEach(SanPham::xuat); }
                case 3 -> { xuLySanPham(); } 
                case 4 -> {}
            }
        } while (chon != 4);
    }

    private void nhapSanPham() {
        System.out.println("1. ThucAn | 2. NuocUong | 3. NonFood");
        int loai = QuanLyKho.readInt("Chọn loại SP: ", 1, 3);        
        SanPham sp;
        if (loai == 1) sp = new ThucAn();
        else if (loai == 2) sp = new NuocUong();
        else sp = new NonFood();
        
        sp.nhap(); 
        
        if (timSanPhamTheoMa(sp.getMa()) != null) {
            System.out.println("Lỗi: Mã sản phẩm " + sp.getMa() + " đã tồn tại trong kho.");
        } else {
            dsSanPham.add(sp);
            luuFile();
            System.out.println("Đã thêm sản phẩm vào kho.");
        }
    }

    private void xuLySanPham() {
        int chon = QuanLyKho.readInt("1. Tìm | 2. Xóa | 3. Sửa: ", 1, 3); 
        String ma = QuanLyKho.readString("Nhập mã sản phẩm (TA/NU/NF + XXX): ");
        Iterator<SanPham> it = dsSanPham.iterator();
        boolean changed = false;
        boolean found = false;
        
        while (it.hasNext()) {
            SanPham sp = it.next();
            if (sp.getMa().equalsIgnoreCase(ma)) {
                found = true;
                if (chon == 1) {
                    System.out.println("Thông tin sản phẩm:");
                    sp.xuat();
                } else if (chon == 2) { 
                    it.remove(); 
                    System.out.println("Đã xóa sản phẩm " + ma); 
                    changed = true; 
                } else if (chon == 3) { 
                    System.out.println("Nhập thông tin mới cho " + ma + " (Mã không thể đổi):");
                    String oldMa = sp.getMa();
                    sp.nhap(); 
                    sp.setMa(oldMa); 
                    System.out.println("Đã sửa sản phẩm " + ma); 
                    changed = true; 
                }
                
                if (changed) luuFile(); 
                break; 
            }
        }
        if (!found) System.out.println("Không tìm thấy sản phẩm!");
    }
    private static final Map<String, Integer> PREFIX_ORDER = new HashMap<>();
    static {
        PREFIX_ORDER.put("TA", 1); // Thức Ăn
        PREFIX_ORDER.put("NU", 2); // Nước Uống
        PREFIX_ORDER.put("NF", 3); // Non-Food
        // Thêm các loại khác nếu cần
    }
    public void luuFile() {
        Collections.sort(dsSanPham, (sp1, sp2) -> {
            String ma1 = sp1.getMa();
            String ma2 = sp2.getMa();
            
            String prefix1 = ma1.substring(0, 2);
            String prefix2 = ma2.substring(0, 2);
            
            // Lấy thứ tự từ Map. Nếu không tìm thấy, gán thứ tự lớn (99) để đẩy xuống cuối
            int order1 = PREFIX_ORDER.getOrDefault(prefix1, 99);
            int order2 = PREFIX_ORDER.getOrDefault(prefix2, 99);

            // 1. So sánh theo tiền tố (dựa vào thứ tự trong Map)
            int prefixComparison = Integer.compare(order1, order2);
            if (prefixComparison != 0) {
                return prefixComparison;
            }

            // 2. Nếu tiền tố giống nhau, so sánh phần số
            try {
                int number1 = Integer.parseInt(ma1.substring(2));
                int number2 = Integer.parseInt(ma2.substring(2));
                return Integer.compare(number1, number2);
            } catch (NumberFormatException e) {
                // Nếu mã không phải số, so sánh chuỗi toàn bộ (fallback)
                return ma1.compareTo(ma2);
            }
        });
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.FILE_NAME))) {
            for (SanPham sp : dsSanPham)
                bw.write(sp.toFileString() + "\n");
        } catch (IOException e) { System.out.println("Lỗi ghi file " + this.FILE_NAME); }
    }
    
    private void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            dsSanPham.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\|");
                if (t.length < 1) continue;
                
                SanPham sp = null;
                try {
                    String loai = t[0];
                    if (loai.equals("ThucAn") && t.length >= 7) {
                        sp = new ThucAn(t[1], t[2], Integer.parseInt(t[3]), Double.parseDouble(t[4]), Date.valueOf(t[5]), t[6]);
                    } else if (loai.equals("NuocUong") && t.length >= 7) {
                        sp = new NuocUong(t[1], t[2], Integer.parseInt(t[3]), Double.parseDouble(t[4]), Date.valueOf(t[5]), t[6]);
                    } else if (loai.equals("NonFood") && t.length >= 6) {                       
                        sp = new NonFood(t[1], t[2], Integer.parseInt(t[3]), Double.parseDouble(t[4]), t[5]);
                    }
                    if (sp != null) dsSanPham.add(sp);
                } catch (Exception e) {
                    System.out.println("Lỗi parse dòng sản phẩm: " + line + " | " + e.getMessage());
                }
            }
            System.out.println("Đã đọc file " + tenFile + " thành công!");
        } catch (IOException e) { System.out.println("Lỗi đọc file " + tenFile); }
    }
}

class QuanLyKhachHang {
    private List<KhachHang> dsKhachHang = new ArrayList<>();
    private final String FILE_NAME = "khachhang.txt";

    public QuanLyKhachHang() { docFile(FILE_NAME); }
    
    public KhachHang timKhachHangTheoMa(String ma) {
        return dsKhachHang.stream()
            .filter(k -> k.getMa().equalsIgnoreCase(ma))
            .findFirst()
            .orElse(null);
    }
                                                                                        
    public void menuKhachHang() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ KHÁCH HÀNG ====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Tìm kiếm (theo mã)");
            System.out.println("4. Xóa khách hàng (theo mã)");
            System.out.println("5. Quay lại");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 5);
            switch (chon) {
                case 1 -> { 
                    KhachHang kh = new KhachHang(); 
                    kh.nhap(); 
                    if (timKhachHangTheoMa(kh.getMa()) != null) {
                        System.out.println("Lỗi: Mã khách hàng " + kh.getMa() + " đã tồn tại.");
                    } else {
                        dsKhachHang.add(kh); 
                        luuFile(FILE_NAME);
                        System.out.println("Đã thêm khách hàng.");
                    }
                }
                case 2 -> { if (dsKhachHang.isEmpty()) System.out.println("Danh sách trống!"); else dsKhachHang.forEach(KhachHang::xuat); }
                case 3 -> {
                    String ma = QuanLyKho.readString("Nhập mã khách cần tìm (KHXXX): "); 
                    KhachHang kh = timKhachHangTheoMa(ma);
                    if (kh != null) {
                        kh.xuat();
                    } else {
                        System.out.println("Không tìm thấy khách hàng " + ma);
                    }
                }
                case 4 -> {
                    String ma = QuanLyKho.readString("Nhập mã khách cần xóa (KHXXX): "); 
                    boolean ok = dsKhachHang.removeIf(k -> k.getMa().equalsIgnoreCase(ma));
                    System.out.println(ok ? "Đã xóa" : "Không tìm thấy");
                    if (ok) luuFile(FILE_NAME); 
                }
                case 5 -> {}
            }
        } while (chon != 5);
    }

    private void luuFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (KhachHang kh : dsKhachHang)
                bw.write(kh.toFileString() + "\n");
        } catch (IOException e) { System.out.println("Lỗi ghi file " + tenFile); }
    }
    
    private void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            dsKhachHang.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\|");
                if (t.length >= 6) { 
                    KhachHang kh = new KhachHang(t[0], t[1], t[2], t[3], t[4], t[5]);
                    dsKhachHang.add(kh);
                }
            }
            System.out.println("Đã đọc file " + tenFile + " thành công!");
        } catch (IOException e) { System.out.println("Lỗi đọc file " + tenFile); }
    }
}
