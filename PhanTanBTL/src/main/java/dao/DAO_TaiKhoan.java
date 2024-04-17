package dao;

import java.util.List;

import entity.TaiKhoan;
import idao.I_DAO_TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_TaiKhoan implements I_DAO_TaiKhoan {

	private EntityManager em;

	public DAO_TaiKhoan() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean themTaiKhoan(TaiKhoan taiKhoan) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(taiKhoan);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

		}
		return false;

	}

	@Override
	public List<TaiKhoan> getDanhSachTaiKhoan() {
		return em.createNamedQuery("taiKhoan.getDanhSachTaiKhoan", TaiKhoan.class).getResultList();
	}

	@Override
	public TaiKhoan getTaiKhoanTheoMaTaiKhoan(String maTK) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("taiKhoan.getTaiKhoanTheoMaTaiKhoan", TaiKhoan.class).setParameter("taiKhoan", maTK)
				.getSingleResult();
	}

	@Override
	public TaiKhoan getMatKhauTheoMaNhanVien(String ma) {
		return em.createNamedQuery("taiKhoan.getMatKhauTheoMaNhanVien", TaiKhoan.class)
				.setParameter("maCongNhanVien", ma).getSingleResult();
	}

	@Override
	public boolean xoaTaiKhoan(String maTaiKhoan) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			TaiKhoan taiKhoan = em.find(TaiKhoan.class, maTaiKhoan);
			em.remove(taiKhoan);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean kiemTraTaiKhoan(String maTK) {
		// TODO Auto-generated method stub
		return em.createQuery("select count(*) from TaiKhoan where taiKhoan = :taiKhoan", Long.class)
				.setParameter("taiKhoan", maTK).getSingleResult() > 0;
	}

}
