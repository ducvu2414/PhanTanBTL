package idao;

import java.sql.Date;
import java.util.List;

import entity.BangChamCongThoLamDan;
import entity.BangPhanCong;

public interface I_DAO_ChamCongThoLamDan {

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTai();

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaSP(String maSP);

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaTenCongDoan(String tenCongDoan);

	public boolean themBangChamCong(BangChamCongThoLamDan bangChamCong);

	public List<BangChamCongThoLamDan> layDanhSachChamCong(Date date);

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTen(Date date, String ten);

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoSP(Date date, String maSP);

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTenCongDoan(Date date, String tenCongDoan);

	public int kiemTraTimKiemTheoTen(String ten);

	public List<Object[]> laySoLuongLamDuocCuaThang(String maThoLamDan, int thang, int nam);
}
