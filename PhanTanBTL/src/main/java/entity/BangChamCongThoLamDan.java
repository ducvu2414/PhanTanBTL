package entity;

import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
public class BangChamCongThoLamDan {
	@Id
	@GeneratedValue(generator = "bcctld_id_gen")
	@GenericGenerator(name = "bcctld_id_gen", strategy = "util.BangChamCongThoLamDanIdGenerator")
	private String maBCCThoLamDan;
	private int soLuongSanPham;
	private Date ngayChamCong;

	@OneToOne
	@JoinColumn(name = "maCongDoan", referencedColumnName = "maCongDoan")
	@JoinColumn(name = "maCongNhanVien", referencedColumnName = "maCongNhanVien")
	@JoinColumn(name = "ngayPhanCong", referencedColumnName = "ngayPhanCong")
	private BangPhanCong bangPhanCong;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maBangLuong", referencedColumnName = "maBangLuong")
	private BangLuongThoLamDan bangLuongThoLamDan;

	

	public String getMaBCCThoLamDan() {
		return maBCCThoLamDan;
	}

	public void setMaBCCThoLamDan(String maBCCThoLamDan) {
		this.maBCCThoLamDan = maBCCThoLamDan;
	}

	public int getSoLuongSanPham() {
		return soLuongSanPham;
	}

	public void setSoLuongSanPham(int soLuongSanPham) {
		this.soLuongSanPham = soLuongSanPham;
	}

	public Date getNgayChamCong() {
		return ngayChamCong;
	}

	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}

	public BangChamCongThoLamDan() {
		super();
	}

	public BangChamCongThoLamDan(String string, String string2, String string3, Long long1) {
		// TODO Auto-generated constructor stub
	}

	public BangPhanCong getBangPhanCong() {
		return bangPhanCong;
	}

	public void setBangPhanCong(BangPhanCong bangPhanCong) {
		this.bangPhanCong = bangPhanCong;
	}

	public BangLuongThoLamDan getBangLuongThoLamDan() {
		return bangLuongThoLamDan;
	}

	public void setBangLuongThoLamDan(BangLuongThoLamDan bangLuongThoLamDan) {
		this.bangLuongThoLamDan = bangLuongThoLamDan;
	}

	@Override
	public String toString() {
		return "BangChamCongThoLamDan [maBCCThoLamDan=" + maBCCThoLamDan + ", soLuongSanPham=" + soLuongSanPham
				+ ", ngayChamCong=" + ngayChamCong + ", bangPhanCong=" + bangPhanCong + ", bangLuongThoLamDan="
				+ bangLuongThoLamDan + "]";
	}

}
