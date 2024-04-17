package idao;

import entity.CongNhanVien;

public interface I_DAO_CongNhanVien {
	public CongNhanVien getCongNhanVienMoiTao();

	public boolean isDuplicate(String hoTen, String maCanCuocCongDan, String soDienThoai);

	public CongNhanVien getCongNhanVienTheoMa(String maCongNhanVien);
}
