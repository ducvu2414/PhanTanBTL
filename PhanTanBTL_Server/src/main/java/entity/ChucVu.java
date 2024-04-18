package entity;

import java.io.Serializable;

public enum ChucVu implements Serializable {
	NhanVien("Nhân Viên"), PhoPhong("Phó Phòng"), TruongPhong("Trưởng Phòng");

	private String name;

	private ChucVu(String name) {
		this.name = name;
	}

	public String getValue() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
