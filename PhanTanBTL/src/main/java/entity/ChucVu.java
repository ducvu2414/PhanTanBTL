package entity;

public enum ChucVu {
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
