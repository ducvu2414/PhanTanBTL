package idao;

import java.util.List;

import entity.CongDoan;

public interface I_DAO_CongDoan {
	public boolean insertCongDoan(CongDoan congDoan);

	public List<CongDoan> getAlListCongDoan();

	public boolean updateCongDoan(CongDoan congDoan);

	public CongDoan getCongDoanTheoMaCongDoan(String maCongDoan);

	public String getMaCongDoan(String maSanPham);

	public CongDoan getCongDoanTheoCDSP(String tenCongDoan, String maSanPham);

	public List<Object[]> getCongDoanTheoTLDNgayThang(String maThoLamDan, int thang, int nam);

	public boolean checkIfExists(String tenCongDoan, String maSanPham);
}
