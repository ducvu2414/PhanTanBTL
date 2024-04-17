package idao;

import java.util.List;

import entity.BangLuongNhanVien;

public interface I_DAO_LuongNhanVien {
	public boolean themBangLuongNhanVien(BangLuongNhanVien bangLuong);

	public boolean updateBangLuongNhanVien(BangLuongNhanVien bangLuong);

	public int laySoNgayDiLamNguyenCaNgayThuong(String maNhanVien, int thang, int nam);

	public int laySoNgayDiLamNuaCaNgayThuong(String maNhanVien, int thang, int nam);

	public int laySoGioTangCaNgayThuong(String maNhanVien, int thang, int nam);

	public int laySoNgayDiLamNguyenCaCN(String maNhanVien, int thang, int nam);

	public int laySoNgayDiLamNuaCaCN(String maNhanVien, int thang, int nam);

	public int laySoGioTangCaCN(String maNhanVien, int thang, int nam);

	public int laySoNgayNghiCoPhep(String maNhanVien, int thang, int nam);

	public int laySoNgayNghiKhongPhep(String maNhanVien, int thang, int nam);

	public String getMaBangLuong(int thang, int nam, String maNhanVien);

	public boolean kiemTraTrungMa(int thang, int nam, String maNhanVien);

	public BangLuongNhanVien getBangLuongTheoMa(String maBangLuong);

	public int kiemTraTimKiemTheoTen(String ten);

	public List<BangLuongNhanVien> getBangLuongTheoTen(String tenNhanVien, int thang, int nam);

	public List<BangLuongNhanVien> getBangLuongTheoPhongBan(String maPhongBan, int thang, int nam);
}
