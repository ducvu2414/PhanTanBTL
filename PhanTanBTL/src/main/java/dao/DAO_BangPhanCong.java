package dao;

import java.sql.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import entity.BangPhanCong;
import idao.I_DAO_BangPhanCong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_BangPhanCong implements I_DAO_BangPhanCong {
	private EntityManager em;

	public DAO_BangPhanCong() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<BangPhanCong> getAlListBangPhanCong() {

		return em.createQuery("SELECT b FROM BangPhanCong b", BangPhanCong.class).getResultList();
	}

	@Override
	public void insertBangPhanCong(BangPhanCong bangPhanCong) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(bangPhanCong);
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
	}

	@Override
	public BangPhanCong getBangPhanCongMoiTao() {
		return em.createQuery("SELECT b FROM BangPhanCong b ORDER BY b.ngayPhanCong DESC Limit 1", BangPhanCong.class)
				.setMaxResults(1).getSingleResult();
	}

	@Override
	public List<BangPhanCong> getBangPhanCongTheoNgayPhanCong(JDateChooser ngayPhanCong) {
		return em.createQuery("SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngayPhanCong", BangPhanCong.class)
				.setParameter("ngayPhanCong", new java.sql.Date(ngayPhanCong.getDate().getTime())).getResultList();

	}

	@Override
	public BangPhanCong getBangPhanCongTheoNgayTLDSP(Date ngay, String maTLD, String maCongDoan) {
		return em.createQuery(
				"SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngay and b.thoLamDan.maCongNhanVien = :maTLD and b.congDoan.maCongDoan = :maCongDoan",
				BangPhanCong.class).setParameter("ngay", ngay).setParameter("maTLD", maTLD)
				.setParameter("maCongDoan", maCongDoan).getSingleResult();
	}

	@Override
	public BangPhanCong getBangPhanCongTheoNgayTLD(Date ngay, String maTLD) {
		return em.createQuery(
				"SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngay and b.thoLamDan.maCongNhanVien = :maTLD",
				BangPhanCong.class).setParameter("ngay", ngay).setParameter("maTLD", maTLD).getSingleResult();
	}

}