package entity;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "NhanVien.docTubang", query = "SELECT nv FROM NhanVien nv"), })
public class NhanVien extends CongNhanVien {

	@Enumerated(EnumType.STRING)
	private ChucVu chucVu;
	@Column(columnDefinition = "nvarchar(255)")
	private String trinhDoVanHoa;
	private double luongCoBan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maPhongBan", referencedColumnName = "maPhongBan")
	private PhongBan phongBan;

	@OneToMany(mappedBy = "nhanVien")
	private Set<BangLuongNhanVien> bangLuongNhanViens;

	public NhanVien(String hoTen, GioiTinh gioiTinh, Date ngaySinh, String maCanCuocCongDan, String soDienThoai,
			String diaChi, boolean trangThai, Date ngayVaoLam, ChucVu chucVu, String trinhDoVanHoa, double luongCoBan) {
		super(hoTen, gioiTinh, ngaySinh, maCanCuocCongDan, soDienThoai, diaChi, trangThai, ngayVaoLam);
		this.chucVu = chucVu;
		this.trinhDoVanHoa = trinhDoVanHoa;
		this.luongCoBan = luongCoBan;
	}

	public NhanVien() {
		super();
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	public String getTrinhDoVanHoa() {
		return trinhDoVanHoa;
	}

	public void setTrinhDoVanHoa(String trinhDoVanHoa) {
		this.trinhDoVanHoa = trinhDoVanHoa;
	}

	public double getLuongCoBan() {
		return luongCoBan;
	}

	public void setLuongCoBan(double luongCoBan) {
		this.luongCoBan = luongCoBan;
	}

	public PhongBan getPhongBan() {
		return phongBan;
	}

	public void setPhongBan(PhongBan phongBan) {
		this.phongBan = phongBan;
	}

	public double tinhHeSoLuong(Date ngayVaoLam, String trinhDoVanHoa) {
		Instant ngayVaoLamInstant = new java.util.Date(ngayVaoLam.getTime()).toInstant();
		LocalDate ngayVaoLamLocal = ngayVaoLamInstant.atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate ngayHienTai = LocalDate.now();
		Period khoangThoiGian = Period.between(ngayVaoLamLocal, ngayHienTai);
		int soNamLamViec = khoangThoiGian.getYears();
		int soBacTangLuong = soNamLamViec / 3;

		double heSoTienTrienCaoDang = 0.31;
		double heSoTienTrienDaiHoc = 0.20;

		double heSoLuong = 0.0;

		if ("Cao Đẳng".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong = 2.1;
		} else if ("Đại Học".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong = 2.41;
		}

		if ("Cao Đẳng".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong += soBacTangLuong * heSoTienTrienCaoDang;
		} else if ("Đại Học".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong += soBacTangLuong * heSoTienTrienDaiHoc;
		}

		return heSoLuong;
	}

	public double tinhHeSoLuong() {
		Instant ngayVaoLamInstant = new java.util.Date(getNgayVaoLam().getTime()).toInstant();
		LocalDate ngayVaoLamLocal = ngayVaoLamInstant.atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate ngayHienTai = LocalDate.now();
		Period khoangThoiGian = Period.between(ngayVaoLamLocal, ngayHienTai);
		int soNamLamViec = khoangThoiGian.getYears();
		int soBacTangLuong = soNamLamViec / 3;

		double heSoTienTrienCaoDang = 0.31;
		double heSoTienTrienDaiHoc = 0.20;

		double heSoLuong = 0.0;

		if ("Cao Đẳng".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong = 2.1;
		} else if ("Đại Học".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong = 2.41;
		}

		if ("Cao Đẳng".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong += soBacTangLuong * heSoTienTrienCaoDang;
		} else if ("Đại Học".equalsIgnoreCase(trinhDoVanHoa)) {
			heSoLuong += soBacTangLuong * heSoTienTrienDaiHoc;
		}

		return heSoLuong;
	}

	@Override
	public String toString() {
		return "NhanVien [chucVu=" + chucVu + ", trinhDoVanHoa=" + trinhDoVanHoa + ", luongCoBan=" + luongCoBan
				+ ", phongBan=" + phongBan + "]";
	}

}
