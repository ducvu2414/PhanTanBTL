package idao;

import java.util.List;

import entity.GioiTinh;
import entity.NhanVien;

public interface I_DAO_NhanVien {
	public List<NhanVien> docTuBang();

	public boolean taoNV(NhanVien nv);

	public List<NhanVien> getAllListNhanVien();

	public NhanVien getNhanVienTheoMa(String maNhanVien);

	public String getMaNhanVienMoiTao();

	public List<NhanVien> getAllNhanVienTheoTen(String ten);

	public List<NhanVien> getAllNhanVienTheoPhongBan(String maPhongBan);

	public boolean update(NhanVien nv);

	public List<NhanVien> getNhanVienTheoTen(String ten);

	public List<NhanVien> getNhanVienTheoPhongBan(String maPhongBan);

	public List<NhanVien> getNhanVienTheoTrangThai(boolean trangThai);

	public List<NhanVien> getAllNhanVienTheoSoDienThoai(String sdt);

	public List<NhanVien> getNhanVienTheoGioiTinh(GioiTinh gioiTinh);

	public List<NhanVien> getAllNhanVienTheoDiaChi(String diaChi);
}
