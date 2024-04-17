package entity;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Testing {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MSSQL");

		emf.close();

	}
}
