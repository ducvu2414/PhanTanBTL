package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class BangPhanCong implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8605859700125476763L;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maCongDoan")
	private CongDoan congDoan;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maCongNhanVien")
	private ThoLamDan thoLamDan;
	@Id
	private Date ngayPhanCong;
	private int soLuongSanPham;

	@Override
	public int hashCode() {
		return Objects.hash(congDoan, ngayPhanCong, thoLamDan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangPhanCong other = (BangPhanCong) obj;
		return Objects.equals(congDoan, other.congDoan) && Objects.equals(ngayPhanCong, other.ngayPhanCong)
				&& Objects.equals(thoLamDan, other.thoLamDan);
	}

	public int getSoLuongSanPham() {
		return soLuongSanPham;
	}

	public void setSoLuongSanPham(int soLuongSanPham) {
		this.soLuongSanPham = soLuongSanPham;
	}

	public CongDoan getCongDoan() {
		return congDoan;
	}

	public void setCongDoan(CongDoan congDoan) {
		this.congDoan = congDoan;
	}

	public ThoLamDan getThoLamDan() {
		return thoLamDan;
	}

	public void setThoLamDan(ThoLamDan thoLamDan) {
		this.thoLamDan = thoLamDan;
	}

	public Date getNgayPhanCong() {
		return ngayPhanCong;
	}

	public void setNgayPhanCong(Date ngayPhanCong) {
		this.ngayPhanCong = ngayPhanCong;
	}

	public BangPhanCong(CongDoan congDoan, ThoLamDan thoLamDan, Date ngayPhanCong) {
		super();
		this.congDoan = congDoan;
		this.thoLamDan = thoLamDan;
		this.ngayPhanCong = ngayPhanCong;
	}

	public BangPhanCong() {
		super();
	}

	@Override
	public String toString() {
		return "BangPhanCong [congDoan=" + congDoan + ", thoLamDan=" + thoLamDan + ", ngayPhanCong=" + ngayPhanCong
				+ "]";
	}

}
