package entity;

import java.sql.Date;
import java.util.Arrays;

import jakarta.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "ThoLamDan.docTubang", query = "SELECT t FROM ThoLamDan t"),
		@NamedQuery(name = "ThoLamDan.getAlListThoLamDan", query = "SELECT t FROM ThoLamDan t Where t.trangThai = true"),
		@NamedQuery(name = "ThoLamDan.getAlListThoLamDanTheoTen", query = "SELECT t FROM ThoLamDan t Where t.hoTen like :hoTen and t.trangThai = true"),
		@NamedQuery(name = "ThoLamDan.getThoLamDanTheoTen", query = "SELECT t FROM ThoLamDan t Where t.hoTen like :hoTen"),
		@NamedQuery(name = "ThoLamDan.getThoLamDanTheoTrangThai", query = "SELECT t FROM ThoLamDan t Where t.trangThai = :trangThai"),
		@NamedQuery(name = "ThoLamDan.getAllThoLamDanTheoSoDienThoai", query = "SELECT t FROM ThoLamDan t Where t.soDienThoai like :soDienThoai"),
		@NamedQuery(name = "ThoLamDan.getThoLamDanTheoGioiTinh", query = "SELECT t FROM ThoLamDan t Where t.gioiTinh = :gioiTinh"),
		@NamedQuery(name = "ThoLamDan.getThoLamDanTheoDiaChi", query = "SELECT t FROM ThoLamDan t Where t.diaChi like :diaChi"),
		@NamedQuery(name = "ThoLamDan.getMaThoLamDanMoiTao", query = "select max(t.maCongNhanVien) from ThoLamDan t ") })

public class ThoLamDan extends CongNhanVien {

	@Enumerated(EnumType.STRING)
	private TayNghe tayNghe;

	public TayNghe getTayNghe() {
		return tayNghe;
	}

	public void setTayNghe(TayNghe tayNghe) {
		this.tayNghe = tayNghe;
	}

	public ThoLamDan() {
		super();
	}

	public ThoLamDan(String hoTen, GioiTinh gioiTinh, Date ngaySinh, String maCanCuocCongDan, String soDienThoai,
			String diaChi, boolean trangThai, Date ngayVaoLam, TayNghe tayNghe) {
		super(hoTen, gioiTinh, ngaySinh, maCanCuocCongDan, soDienThoai, diaChi, trangThai, ngayVaoLam);
		this.tayNghe = tayNghe;
	}

	public ThoLamDan(String maCongNhanVien, String hoTen, GioiTinh gioiTinh, Date ngaySinh, String maCanCuocCongDan,
			String soDienThoai, String diaChi, boolean trangThai, Date ngayVaoLam) {
		super(maCongNhanVien, hoTen, gioiTinh, ngaySinh, maCanCuocCongDan, soDienThoai, diaChi, trangThai, ngayVaoLam);
	}

	@Override
	public String toString() {
		return "ThoLamDan [getMaCongNhanVien()=" + getMaCongNhanVien() + ", getHoTen()=" + getHoTen()
				+ ", isGioiTinh()=" + getGioiTinh().getValue() + ", getNgaySinh()=" + getNgaySinh()
				+ ", getMaCanCuocCongDan()=" + getMaCanCuocCongDan() + ", getSoDienThoai()=" + getSoDienThoai()
				+ ", getDiaChi()=" + getDiaChi() + ", isTrangThai()=" + isTrangThai() + ", getNgayVaoLam()="
				+ getNgayVaoLam() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", tayNghe=" + tayNghe + "]";
	}

	public double tinhHeSoLuong() {
		String[] trinhDo = { "Bậc 1", "Bậc 2", "Bậc 3", "Bậc 4", "Bậc 5" };
		double[] heSo = { 1.0, 1.2, 1.4, 1.6, 1.8 };

		int position = Arrays.asList(trinhDo).indexOf(tayNghe.getValue());
		double heSoLuong = heSo[position];
		return heSoLuong;
	}
}
