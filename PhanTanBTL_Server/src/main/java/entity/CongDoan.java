package entity;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "CongDoan.getAllListCD", query = "SELECT c FROM CongDoan c"),
		@NamedQuery(name = "CongDoan.getMaCDTheoMaSP", query = "SELECT c.maCongDoan FROM CongDoan c WHERE c.dan.maSanPham = :maSanPham"),
		@NamedQuery(name = "CongDoan.getCDTheoCDSP", query = "SELECT c FROM CongDoan c WHERE c.tenCongDoan = :tenCongDoan AND c.dan.maSanPham = :maSanPham"),
		@NamedQuery(name = "CongDoan.checkIfExists", query = "SELECT COUNT(c) FROM CongDoan c WHERE c.tenCongDoan = :tenCongDoan AND c.dan.maSanPham = :maSanPham") })
public class CongDoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -24667972784190290L;
	@Id
	@GeneratedValue(generator = "GenerateMaCD")
	@GenericGenerator(name = "GenerateMaCD", strategy = "util.CongDoanIdGenerator")
	private String maCongDoan;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenCongDoan;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maSanPham", referencedColumnName = "maSanPham")
	private Dan dan;
	private float giaCongDoan;

	public String getMaCongDoan() {
		return maCongDoan;
	}

	public void setMaCongDoan(String maCongDoan) {
		this.maCongDoan = maCongDoan;
	}

	public String getTenCongDoan() {
		return tenCongDoan;
	}

	public void setTenCongDoan(String tenCongDoan) {
		this.tenCongDoan = tenCongDoan;
	}

	public Dan getDan() {
		return dan;
	}

	public void setDan(Dan dan) {
		this.dan = dan;
	}

	public float getGiaCongDoan() {
		return giaCongDoan;
	}

	public void setGiaCongDoan(float giaCongDoan) {
		this.giaCongDoan = giaCongDoan;
	}

	public CongDoan(String maCongDoan, String tenCongDoan, Dan dan, float giaCongDoan) {
		super();
		this.maCongDoan = maCongDoan;
		this.tenCongDoan = tenCongDoan;
		this.dan = dan;
		this.giaCongDoan = giaCongDoan;
	}

	public CongDoan() {
		super();
	}

	@Override
	public String toString() {
		return "CongDoan [maCongDoan=" + maCongDoan + ", tenCongDoan=" + tenCongDoan + ", dan=" + dan + ", giaCongDoan="
				+ giaCongDoan + "]";
	}

}
