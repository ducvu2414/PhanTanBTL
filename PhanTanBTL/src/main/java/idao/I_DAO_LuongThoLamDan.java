package idao;

import java.util.List;

import entity.BangLuongThoLamDan;

public interface I_DAO_LuongThoLamDan {
	public boolean themBangLuongThoLamDan(BangLuongThoLamDan bangLuong);

	public boolean updateBangLuongThoLamDan(BangLuongThoLamDan bangLuong);

	public String getMaBangLuong(int thang, int nam, String maThoLamDan);

	public boolean kiemTraTrungMa(int thang, int nam, String maThoLamDan);

	public BangLuongThoLamDan getBangLuongTheoMa(String maBangLuong);

	public int laySoSanPham(String maThoLamDan, int thang, int nam);

	public double layTongThuNhapTungThang(String maThoLamDan, int thang, int nam);

	public int kiemTraTimKiemTheoTen(String ten);

	public List<BangLuongThoLamDan> getBangLuongTheoTen(String tenThoLamDan, int thang, int nam);

}
