package entity;

import jakarta.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "taiKhoan.getDanhSachTaiKhoan", query = "select t from TaiKhoan t"),
		@NamedQuery(name = "taiKhoan.getTaiKhoanTheoMaTaiKhoan", query = "select t from TaiKhoan t where t.taiKhoan = :taiKhoan"),
		@NamedQuery(name = "taiKhoan.getMatKhauTheoMaNhanVien", query = "select t from TaiKhoan t where t.nhanVien.maCongNhanVien = :maCongNhanVien")

})
public class TaiKhoan {
	@Id
	private String taiKhoan;
	private String matKhau;
	@OneToOne
	@JoinColumn(name = "maCongNhanVien", unique = true, nullable = false)
	private NhanVien nhanVien;

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String taiKhoan, String matKhau, NhanVien nhanVien) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.nhanVien = nhanVien;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public String toString() {
		return "TaiKhoan [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + "]";
	}

}
