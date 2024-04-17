package dao;

import java.util.List;
import entity.CongDoan;
import idao.I_DAO_CongDoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_CongDoan implements I_DAO_CongDoan {
	private EntityManager em;

	public DAO_CongDoan() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean insertCongDoan(CongDoan congDoan) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(congDoan);
			et.commit();
			return true;
		} catch (Exception e) {
			et.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CongDoan> getAlListCongDoan() {
		return em.createNamedQuery("CongDoan.getAllListCD", CongDoan.class).getResultList();
	}

	@Override
	public boolean updateCongDoan(CongDoan congDoan) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(congDoan);
			et.commit();
			return true;
		} catch (Exception e) {
			et.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CongDoan getCongDoanTheoMaCongDoan(String maCongDoan) {
		return em.find(CongDoan.class, maCongDoan);
	}

	@Override
	public String getMaCongDoan(String maSanPham) {

		return em.createNamedQuery("CongDoan.getMaCDTheoMaSP", String.class).setParameter("maSanPham", maSanPham)
				.getSingleResult();
	}

	@Override
	public CongDoan getCongDoanTheoCDSP(String tenCongDoan, String maSanPham) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			CongDoan congDoan = (CongDoan) em.createNamedQuery("CongDoan.getCDTheoCDSP")
					.setParameter("tenCongDoan", tenCongDoan).setParameter("maSanPham", maSanPham).getSingleResult();
			et.commit();
			return congDoan;
		} catch (Exception e) {
			et.rollback();
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCongDoanTheoTLDNgayThang(String maThoLamDan, int thang, int nam) {
		String sql = "SELECT " + "    TLD.maCongNhanVien, " + "    CD.maSanPham, " + "    CD.maCongDoan, "
				+ "    SUM(BCCTLD.soLuongSanPham) AS SoLuongLamDuoc " + "FROM " + "    BangChamCongThoLamDan BCCTLD "
				+ "JOIN " + "    ThoLamDan TLD ON BCCTLD.maCongNhanVien = TLD.maCongNhanVien " + "JOIN "
				+ "    BangPhanCong BPC ON BCCTLD.maCongNhanVien = BPC.maCongNhanVien AND BCCTLD.maCongDoan = BPC.maCongDoan "
				+ "JOIN " + "    CongDoan CD ON BPC.maCongDoan = CD.maCongDoan " + "WHERE "
				+ "    TLD.maCongNhanVien = :maCongNhanVien " + "    AND MONTH(BCCTLD.ngayChamCong) = :thang "
				+ "    AND YEAR(BCCTLD.ngayChamCong) = :nam " + "GROUP BY " + "    TLD.maCongNhanVien, "
				+ "    CD.maSanPham, " + "    CD.maCongDoan";

		return em.createNativeQuery(sql, Object[].class).setParameter("maCongNhanVien", maThoLamDan)
				.setParameter("thang", thang).setParameter("nam", nam).getResultList();
	}

	@Override
	public boolean checkIfExists(String tenCongDoan, String maSanPham) {

		long count = em.createNamedQuery("CongDoan.checkIfExists", Long.class).setParameter("tenCongDoan", tenCongDoan)
				.setParameter("maSanPham", maSanPham).getSingleResult();
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

}
