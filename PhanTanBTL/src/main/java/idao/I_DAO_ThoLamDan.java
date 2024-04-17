package idao;

import java.util.List;

import entity.GioiTinh;
import entity.ThoLamDan;

public interface I_DAO_ThoLamDan {
	public List<ThoLamDan> docTuBang();

	public boolean taoTLD(ThoLamDan tld);

	public String getMaThoLamDanMoiTao();

	public List<ThoLamDan> getAlListThoLamDan();

	public ThoLamDan getTLDTheoMaThoLamDan(String maThoLamDan);

	public List<ThoLamDan> getAlListThoLamDanTheoTen(String ten);

	public List<ThoLamDan> getThoLamDanTheoTen(String ten);

	public List<ThoLamDan> getThoLamDanTheoTrangThai(boolean trangThai);

	public List<ThoLamDan> getAllThoLamDanTheoSoDienThoai(String sdt);

	public List<ThoLamDan> getThoLamDanTheoGioiTinh(GioiTinh gioiTinh);

	public List<ThoLamDan> getThoLamDanTheoDiaChi(String diaChi);

	public boolean update(ThoLamDan tld);
}
