package entity;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
public class BangLuongThoLamDan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -742643594268534160L;
	@Id
	@GeneratedValue(generator = "blnv_id_gen")
	@GenericGenerator(name = "blnv_id_gen", strategy = "util.BangLuongThoLamDanIdGenerator")
	private String maBangLuong;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maThoLamDan", referencedColumnName = "maCongNhanVien")
	private ThoLamDan thoLamDan;
	private int thang;
	private int nam;
	private int soLuongSanPham;

	public int getSoLuongSanPham() {
		return soLuongSanPham;
	}

	public void setSoLuongSanPham(int soLuongSanPham) {
		this.soLuongSanPham = soLuongSanPham;
	}

	public String getMaBangLuong() {
		return maBangLuong;
	}

	public void setMaBangLuong(String maBangLuong) {
		this.maBangLuong = maBangLuong;
	}

	public ThoLamDan getThoLamDan() {
		return thoLamDan;
	}

	public void setThoLamDan(ThoLamDan thoLamDan) {
		this.thoLamDan = thoLamDan;
	}

	public int getThang() {
		return thang;
	}

	public void setThang(int thang) {
		this.thang = thang;
	}

	public int getNam() {
		return nam;
	}

	public void setNam(int nam) {
		this.nam = nam;
	}

	public BangLuongThoLamDan(String maBangLuong, ThoLamDan thoLamDan, int thang, int nam, int soLuongSanPham) {
		super();
		this.maBangLuong = maBangLuong;
		this.thoLamDan = thoLamDan;
		this.thang = thang;
		this.nam = nam;
		this.soLuongSanPham = soLuongSanPham;
	}

	@Override
	public String toString() {
		return "LuongThoLamDan [maBangLuong=" + maBangLuong + ", thoLamDan=" + thoLamDan + ", thang=" + thang + ", nam="
				+ nam + "]";
	}

	public double tinhLuongThucLinh(double luongThucTe) {
		double luongThucLinh = luongThucTe - (3000000 * 0.08 + 3000000 * 0.015 + 3000000 * 0.01) + 900000
				+ thoLamDan.tinhPhuCapThamNienThoLamDan(thoLamDan.tinhHeSoLuong());
		return luongThucLinh;
	}

	public BangLuongThoLamDan() {
		super();
	}

}
