package entity;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
public class BangChamCongNhanVien implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8617233845595789118L;
	@Id
	@GeneratedValue(generator = "bccnv_id_gen")
	@GenericGenerator(name = "bccnv_id_gen", strategy = "util.BangChamCongNhanVienIdGenerator")
	private String maBCCNhanVien;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maCongNhanVien", referencedColumnName = "maCongNhanVien")
	private NhanVien nhanVien;
	private Date ngayChamCong;
	private String trangThaiDiLam;
	private int soGioTangCa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maBangLuong", referencedColumnName = "maBangLuong")
	private BangLuongNhanVien bangLuong;

	public String getMaBCCNhanVien() {
		return maBCCNhanVien;
	}

	public void setMaBCCNhanVien(String maBCCNhanVien) {
		this.maBCCNhanVien = maBCCNhanVien;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Date getNgayChamCong() {
		return ngayChamCong;
	}

	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}

	public String getTrangThaiDiLam() {
		return trangThaiDiLam;
	}

	public void setTrangThaiDiLam(String trangThaiDiLam) {
		this.trangThaiDiLam = trangThaiDiLam;
	}

	public int getSoGioTangCa() {
		return soGioTangCa;
	}

	public void setSoGioTangCa(int soGioTangCa) {
		this.soGioTangCa = soGioTangCa;
	}

	public BangLuongNhanVien getBangLuong() {
		return bangLuong;
	}

	public void setBangLuong(BangLuongNhanVien bangLuong) {
		this.bangLuong = bangLuong;
	}

	public BangChamCongNhanVien(String maBCCNhanVien, NhanVien nhanVien, Date ngayChamCong, String trangThaiDiLam,
			int soGioTangCa, BangLuongNhanVien bangLuong) {
		super();
		this.maBCCNhanVien = maBCCNhanVien;
		this.nhanVien = nhanVien;
		this.ngayChamCong = ngayChamCong;
		this.trangThaiDiLam = trangThaiDiLam;
		this.soGioTangCa = soGioTangCa;
		this.bangLuong = bangLuong;
	}

	public BangChamCongNhanVien() {
		super();
	}

	@Override
	public String toString() {
		return "ChamCongNhanVien [maBCCNhanVien=" + maBCCNhanVien + ", nhanVien=" + nhanVien + ", ngayChamCong="
				+ ngayChamCong + ", trangThaiDiLam=" + trangThaiDiLam + ", soGioTangCa=" + soGioTangCa + ", bangLuong="
				+ bangLuong + "]";
	}

}
