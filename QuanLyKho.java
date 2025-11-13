import java.io.*;
import java.util.*;
import java.sql.Date; 
import java.util.regex.Pattern;
import java.util.Arrays;

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
        qlPhieu = new QuanLyPhieu(); 
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
            String s = readString(prompt); // Đảm bảo không rỗng
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
    public static void main(String[] args) {
        new QuanLyKho().hienMenu();
    }
}


class QuanLyPhieu {
    private List<Phieu> danhSachPhieu = new ArrayList<>();
    private final String FILE_NAME = "phieu.txt";

    public QuanLyPhieu() {
        docFile(FILE_NAME); 
    }

    public void menuPhieu() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ PHIẾU ====");
            System.out.println("1. Nhập phiếu nhập hàng");
            System.out.println("2. Nhập phiếu xuất hàng");
            System.out.println("3. Xem tất cả phiếu");
            System.out.println("4. Tìm/Xóa/Sửa phiếu");
            System.out.println("5. Thống kê");
            System.out.println("6. Quay lại menu chính");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 6);            
            switch (chon) {
                case 1 -> nhapPhieuNhap();
                case 2 -> nhapPhieuXuat();
                case 3 -> xemTatCa();
                case 4 -> menuXuLyPhieu();
                case 5 -> thongKe();
                case 6 -> {}
            }
        } while (chon != 6);
    }

    private void nhapPhieuNhap() {
        PhieuNhapHang p = new PhieuNhapHang();
        p.nhap();
        danhSachPhieu.add(p);
        luuFile(FILE_NAME); 
    }

    private void nhapPhieuXuat() {
        PhieuXuatHang p = new PhieuXuatHang();
        p.nhap();
        danhSachPhieu.add(p);
        luuFile(FILE_NAME); 
    }

    private void xemTatCa() {
        if (danhSachPhieu.isEmpty()) System.out.println("Danh sách rỗng!");
        else for (Phieu p : danhSachPhieu) p.xuat();
    }

    private void menuXuLyPhieu() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ PHIẾU CHI TIẾT ====");
            System.out.println("1. Tìm kiếm phiếu");
            System.out.println("2. Xóa phiếu");
            System.out.println("3. Sửa phiếu");
            System.out.println("4. Quay lại");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 4); 
            switch (chon) {
                case 1 -> timKiem();
                case 2 -> xoaPhieu();
                case 3 -> suaPhieu();
                case 4 -> {}
                }
        } while (chon != 4);
    }

    private void luuFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (Phieu p : danhSachPhieu) {
                bw.write(p.toFileString()); 
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file phiếu: " + e.getMessage());
        }
    }

    private void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) {
            System.out.println("File " + tenFile + " không tồn tại, tạo mới.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            danhSachPhieu.clear();
            String line;
            Phieu p = null;
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\|");
                if (t.length == 0) continue;

                String type = t[0];
                if (type.equals("PhieuNhapHang") || type.equals("PhieuXuatHang")) {
                    p = type.equals("PhieuNhapHang") ? new PhieuNhapHang() : new PhieuXuatHang();
                    p.setMaPhieu(t[1]);
                    try { p.ngayLap = java.sql.Date.valueOf(t[2]); } catch (Exception e) { p.ngayLap = new java.util.Date(); }
                    p.dsSanPham.clear(); 
                } else if (type.equals("NV") && p != null) {
                    p.nhanVien = new NhanVienKho(t[1], t[2], t[3], t[4], t[5]);
                } else if (type.equals("SP") && p != null) {
                    SanPham sp = null;
                    try {
                        if (t[1].equals("ThucAn")) {
                            sp = new ThucAn(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), java.sql.Date.valueOf(t[6]), t[7]);
                        } else if (t[1].equals("NuocUong")) {
                            sp = new NuocUong(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), java.sql.Date.valueOf(t[6]), t[7]);
                        } else if (t[1].equals("NonFood")) {
                            sp = new NonFood(t[2], t[3], Integer.parseInt(t[4]), Double.parseDouble(t[5]), t[6]);
                        }
                        if (sp != null) p.dsSanPham.add(sp);
                    } catch (Exception e) { System.out.println("Lỗi parse dòng SP trong Phieu: " + line); }
                } else if (type.equals("END_PHIEU")) {
                    if (p != null) {
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
        String ma = QuanLyKho.readString("Nhập mã phiếu cần tìm: "); 
        boolean tim = false;
        for (Phieu p : danhSachPhieu)
            if (p.getMaPhieu().equalsIgnoreCase(ma)) {
                p.xuat();
                tim = true;
            }
        if (!tim) System.out.println("Không tìm thấy phiếu!");
    }
    
    private void xoaPhieu() {
        String ma = QuanLyKho.readString("Nhập mã phiếu cần xóa: "); 
        boolean xoa = danhSachPhieu.removeIf(p -> p.getMaPhieu().equalsIgnoreCase(ma));
        if (xoa) {
            System.out.println("Đã xóa phiếu " + ma);
            luuFile(FILE_NAME); 
        }
        else System.out.println("Không tìm thấy phiếu " + ma);
    }

    private void suaPhieu() {
        String ma = QuanLyKho.readString("Nhập mã phiếu cần sửa: "); 
        for (Phieu p : danhSachPhieu)
            if (p.getMaPhieu().equalsIgnoreCase(ma)) {
                p.nhap(); 
                System.out.println("Đã cập nhật phiếu " + ma);
                luuFile(FILE_NAME); 
                return;
            }
        System.out.println("Không tìm thấy phiếu " + ma);
    }

    private void thongKe() {
        long soNhap = danhSachPhieu.stream().filter(p -> p instanceof PhieuNhapHang).count();
        long soXuat = danhSachPhieu.stream().filter(p -> p instanceof PhieuXuatHang).count();
        double tongNhap = 0, tongXuat = 0;
        for (Phieu p : danhSachPhieu) {
            if (p instanceof PhieuNhapHang) tongNhap += p.tinhTongTien();
            else if (p instanceof PhieuXuatHang) tongXuat += p.tinhTongTien();
        }
        System.out.println("\n===== THỐNG KÊ =====");
        System.out.println("Phiếu nhập: " + soNhap + " | Tổng tiền nhập: " + tongNhap);
        System.out.println("Phiếu xuất: " + soXuat + " | Tổng tiền xuất: " + tongXuat);
    }
}


class QuanLyNhanVien {
    private List<NhanVienKho> dsNhanVien = new ArrayList<>();
    private final String FILE_NAME = "nhanvien.txt";

    public QuanLyNhanVien() {
        docFile(FILE_NAME); 
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
                    dsNhanVien.add(nv);
                    luuFile(FILE_NAME);
                }
                case 2 -> { if (dsNhanVien.isEmpty()) System.out.println("Danh sách trống!"); else dsNhanVien.forEach(NhanVienKho::xuat); }
                case 3 -> {
                    String ma = QuanLyKho.readString("Nhập mã nhân viên cần xóa: "); 
                    boolean ok = dsNhanVien.removeIf(n -> n.getMaNV().equalsIgnoreCase(ma));
                    System.out.println(ok ? "Đã xóa" : "Không tìm thấy");
                    if (ok) luuFile(FILE_NAME);
                }
                case 4 -> {
                    String ma = QuanLyKho.readString("Nhập mã nhân viên cần tìm: "); 
                    dsNhanVien.stream().filter(n -> n.getMaNV().equalsIgnoreCase(ma)).forEach(NhanVienKho::xuat);
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
                if (t.length >= 5) {
                    NhanVienKho nv = new NhanVienKho(t[0], t[1], t[2], t[3], t[4]);
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

    public QuanLySanPham() {
        docFile(FILE_NAME); 
    }

    public void menuSanPham() {
        int chon;
        do {
            System.out.println("\n==== QUẢN LÝ SẢN PHẨM ====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Tìm/Xóa/Sửa sản phẩm");
            System.out.println("4. Quay lại");
            System.out.print("");
            chon = QuanLyKho.readInt("Chọn: ", 1, 4);
            switch (chon) {
                case 1 -> { nhapSanPham(); luuFile(FILE_NAME); } 
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
        dsSanPham.add(sp);
    }

    private void xuLySanPham() {
        int chon = QuanLyKho.readInt("1. Tìm | 2. Xóa | 3. Sửa: ", 1, 3); 
        String ma = QuanLyKho.readString("Nhập mã sản phẩm: ");
        Iterator<SanPham> it = dsSanPham.iterator();
        boolean changed = false;
        
        while (it.hasNext()) {
            SanPham sp = it.next();
            if (sp.getMa().equalsIgnoreCase(ma)) {
                if (chon == 1) sp.xuat();
                else if (chon == 2) { 
                    it.remove(); 
                    System.out.println("Đã xóa"); 
                    changed = true; 
                }
                else if (chon == 3) { 
                    sp.nhap(); 
                    System.out.println("Đã sửa"); 
                    changed = true; 
                }
                
                if (changed) luuFile(FILE_NAME); // Tự động lưu nếu có thay đổi
                return;
            }
        }
        System.out.println("Không tìm thấy sản phẩm!");
    }

    private void luuFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (SanPham sp : dsSanPham)
                bw.write(sp.toFileString() + "\n");
        } catch (IOException e) { System.out.println("Lỗi ghi file " + tenFile); }
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
                    System.out.println("Lỗi parse dòng sản phẩm: " + line);
                }
            }
            System.out.println("Đã đọc file " + tenFile + " thành công!");
        } catch (IOException e) { System.out.println("Lỗi đọc file " + tenFile); }
    }
}

class QuanLyKhachHang {
    private List<KhachHang> dsKhachHang = new ArrayList<>();
    private final String FILE_NAME = "khachhang.txt";

    public QuanLyKhachHang() {
        docFile(FILE_NAME); 
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
                    dsKhachHang.add(kh); 
                    luuFile(FILE_NAME); 
                }
                case 2 -> { if (dsKhachHang.isEmpty()) System.out.println("Danh sách trống!"); else dsKhachHang.forEach(KhachHang::xuat); }
                case 3 -> {
                    String ma = QuanLyKho.readString("Nhập mã khách cần tìm: "); 
                    long count = dsKhachHang.stream().filter(k -> k.getMa().equalsIgnoreCase(ma)).peek(KhachHang::xuat).count();                }
                case 4 -> {
                    String ma = QuanLyKho.readString("Nhập mã khách cần xóa: "); 
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