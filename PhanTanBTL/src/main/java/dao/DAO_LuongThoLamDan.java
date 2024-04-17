package dao;

import java.util.List;
import java.util.Optional;

import entity.BangLuongThoLamDan;
import idao.I_DAO_LuongThoLamDan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DAO_LuongThoLamDan implements I_DAO_LuongThoLamDan {
	EntityManager em;

	public DAO_LuongThoLamDan() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean themBangLuongThoLamDan(BangLuongThoLamDan bangLuong) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(bangLuong);
			entityTransaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

	@Override
	public boolean updateBangLuongThoLamDan(BangLuongThoLamDan bangLuong) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.merge(bangLuong);
			entityTransaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

	@Override
	public String getMaBangLuong(int thang, int nam, String maThoLamDan) {
		String jpql = "SELECT bltld.maBangLuong " + "FROM BangLuongThoLamDan bltld " + "WHERE thang = :thang "
				+ "AND nam = :nam " + "AND bltld.thoLamDan.maCongNhanVien = :maThoLamDan";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		query.setParameter("maThoLamDan", maThoLamDan);
		return query.getSingleResult();
	}

	@Override
	public boolean kiemTraTrungMa(int thang, int nam, String maThoLamDan) {
		String jpql = "SELECT COUNT(bltld.maBangLuong) " + "FROM BangLuongThoLamDan bltld " + "WHERE thang = :thang "
				+ "AND nam = :nam " + "AND bltld.thoLamDan.maCongNhanVien = :maThoLamDan";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		query.setParameter("maThoLamDan", maThoLamDan);
		int count = query.getSingleResult().intValue();
		return count != 0 ? true : false;
	}

	@Override
	public BangLuongThoLamDan getBangLuongTheoMa(String maBangLuong) {
		return em.find(BangLuongThoLamDan.class, maBangLuong);
	}

	@Override
	public int laySoSanPham(String maThoLamDan, int thang, int nam) {
		List<Long> results = em.createQuery(
				"SELECT SUM(bcc.soLuongSanPham) FROM BangChamCongThoLamDan bcc WHERE bcc.bangPhanCong.thoLamDan.maCongNhanVien = :maThoLamDan "
						+ "AND MONTH(bcc.ngayChamCong) = :thang "
						+ "AND YEAR(bcc.ngayChamCong) = :nam GROUP BY bcc.bangPhanCong.thoLamDan.maCongNhanVien",
				Long.class).setParameter("maThoLamDan", maThoLamDan).setParameter("thang", thang)
				.setParameter("nam", nam).getResultList();

		if (results.isEmpty()) {
			return 0;
		} else {
			return results.stream().mapToInt(Long::intValue).sum();
		}
	}

	@Override
	public double layTongThuNhapTungThang(String maThoLamDan, int thang, int nam) {
		Optional<Double> result = Optional.ofNullable(em
				.createQuery(
						"Select SUM(CD.giaCongDoan * BCCTLD.soLuongSanPham) " + "from BangChamCongThoLamDan BCCTLD "
								+ "join BCCTLD.bangPhanCong BPCTLD " + "join BPCTLD.congDoan CD "
								+ "where BCCTLD.bangPhanCong.thoLamDan.maCongNhanVien = :maThoLamDan "
								+ "and MONTH(BCCTLD.ngayChamCong) = :thang " + "and YEAR(BCCTLD.ngayChamCong) = :nam",
						Double.class)
				.setParameter("maThoLamDan", maThoLamDan).setParameter("thang", thang).setParameter("nam", nam)
				.getSingleResult());

		return result.orElse(0.0);
	}

	@Override
	public int kiemTraTimKiemTheoTen(String ten) {
		Long kt = em.createQuery("SELECT COUNT(tld.maCongNhanVien) FROM ThoLamDan tld where tld.hoTen LIKE :ten",
				Long.class).setParameter("ten", "%" + ten + "%").getSingleResult();
		return kt.intValue();
	}

	@Override
	public List<BangLuongThoLamDan> getBangLuongTheoTen(String tenThoLamDan, int thang, int nam) {
		return em
				.createQuery(
						"SELECT bltld FROM BangLuongThoLamDan bltld WHERE bltld.thoLamDan.hoTen like :tenThoLamDan "
								+ "AND thang = :thang " + "AND nam = :nam",
						BangLuongThoLamDan.class)
				.setParameter("tenThoLamDan", "%" + tenThoLamDan + "%").setParameter("thang", thang)
				.setParameter("nam", nam).getResultList();
	}

}
