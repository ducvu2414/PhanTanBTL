package dao;

import java.util.List;

import entity.Dan;
import idao.I_DAO_SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_SanPham implements I_DAO_SanPham {
	private EntityManager em;

	public DAO_SanPham() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<Dan> docTuBang() {
		return em.createQuery("Select d from Dan d", Dan.class).getResultList();
	}

	@Override
	public boolean taoSP(Dan dan) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(dan);
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

	@Override
	public String getMaSanPhamMoiTao() {
		return em.createQuery("Select d.maSanPham from Dan d order by d.maSanPham desc limit 1", String.class)
				.setMaxResults(1).getSingleResult();
	}

	@Override
	public boolean update(Dan dan) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.merge(dan);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

}
