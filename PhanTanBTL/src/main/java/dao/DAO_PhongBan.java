package dao;

import java.util.List;

import entity.PhongBan;
import idao.I_DAO_PhongBan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_PhongBan implements I_DAO_PhongBan {
	private EntityManager em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();

	@Override
	public List<PhongBan> docTuBang() {
		return em.createQuery("Select p from PhongBan p", PhongBan.class).getResultList();
	}

	@Override
	public PhongBan getPhongBanTheoTen(String tenPhongBan) {
		return em.createQuery("select p from PhongBan p where p.tenPhongBan = :tenPhongBan", PhongBan.class)
				.setParameter("tenPhongBan", tenPhongBan).getSingleResult();
	}

	@Override
	public PhongBan getPhongBanTheoMa(String maPhongBan) {
		return em.find(PhongBan.class, maPhongBan);
	}

	@Override
	public List<PhongBan> getTatCaPhongBan() {
		return em.createQuery("select p from PhongBan p", PhongBan.class).getResultList();
	}

}
