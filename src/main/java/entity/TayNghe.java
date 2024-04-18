package entity;

import java.io.Serializable;

public enum TayNghe implements Serializable {
	Bac1("Bậc 1"), Bac2("Bậc 2"), Bac3("Bậc 3"), Bac4("Bậc 4"), Bac5("Bậc 5");

	private String name;

	private TayNghe(String name) {
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
