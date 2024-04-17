package entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PhongBan {
	@Id
	private String maPhongBan;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenPhongBan;

	@OneToMany(mappedBy = "phongBan")
	private Set<NhanVien> nhanViens;

	public String getMaPhongBan() {
		return maPhongBan;
	}

	public void setMaPhongBan(String maPhongBan) {
		this.maPhongBan = maPhongBan;
	}

	public String getTenPhongBan() {
		return tenPhongBan;
	}

	public void setTenPhongBan(String tenPhongBan) {
		this.tenPhongBan = tenPhongBan;
	}

	public PhongBan(String maPhongBan, String tenPhongBan) {
		super();
		this.maPhongBan = maPhongBan;
		this.tenPhongBan = tenPhongBan;
	}

	public PhongBan() {
		super();
	}

	public PhongBan(String tenPhongBan) {
		super();
		this.tenPhongBan = tenPhongBan;
	}

	@Override
	public String toString() {
		return "PhongBan [maPhongBan=" + maPhongBan + ", tenPhongBan=" + tenPhongBan + "]";
	}

}
