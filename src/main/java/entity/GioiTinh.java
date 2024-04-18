package entity;

import java.io.Serializable;

public enum GioiTinh implements Serializable {

//	MALE, FEMALE, OTHER;

	Nam("Nam"), Nu("Nữ");

	private String name;

	public String getValue() {
		return name;
	}

	private GioiTinh(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
