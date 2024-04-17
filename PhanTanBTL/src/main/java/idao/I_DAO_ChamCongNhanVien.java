package idao;

import java.sql.Date;
import java.util.List;

import entity.BangChamCongNhanVien;

public interface I_DAO_ChamCongNhanVien {
	public boolean themBangChamCong(BangChamCongNhanVien bangChamCong);

	public boolean KiemTraTrung(BangChamCongNhanVien bangChamCong);

	public int kiemTraSoLuongDaChamCong(Date date);

	public int kiemTraTimKiemTheoTen(String ten);

	public List<BangChamCongNhanVien> layDanhSachChamCong(Date date);

	public List<BangChamCongNhanVien> layDanhSachChamCongTheoTen(Date date, String ten);

	public List<BangChamCongNhanVien> layDanhSachChamCongTheoPhongBan(Date date, String maPhongBan);
}
