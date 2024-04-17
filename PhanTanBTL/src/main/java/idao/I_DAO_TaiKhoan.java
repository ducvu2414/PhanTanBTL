package idao;

import java.util.List;

import entity.TaiKhoan;

public interface I_DAO_TaiKhoan {
	public boolean themTaiKhoan(TaiKhoan taiKhoan);

	public boolean xoaTaiKhoan(String maTaiKhoan);

	public boolean kiemTraTaiKhoan(String maTK);

	public List<TaiKhoan> getDanhSachTaiKhoan();

	public TaiKhoan getTaiKhoanTheoMaTaiKhoan(String maTK);

	public TaiKhoan getMatKhauTheoMaNhanVien(String ma);
}
