package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

import entity.BangChamCongThoLamDan;
import entity.BangPhanCong;
import idao.I_DAO_ChamCongThoLamDan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class DAO_ChamCongThoLamDan extends UnicastRemoteObject implements I_DAO_ChamCongThoLamDan {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5666375477946414264L;
	private EntityManager em;

	public DAO_ChamCongThoLamDan() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTai() throws RemoteException {
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		return em.createQuery("SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngayPhanCong", BangPhanCong.class)
				.setParameter("ngayPhanCong", date).getResultList();
	}

	@Override
	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaSP(String maSP) throws RemoteException {
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		return em.createQuery(
				"SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngayPhanCong and b.congDoan.dan.maSanPham = :maSP",
				BangPhanCong.class).setParameter("ngayPhanCong", date).setParameter("maSP", maSP).getResultList();
	}

	@Override
	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaTenCongDoan(String tenCongDoan)
			throws RemoteException {
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		return em.createQuery(
				"SELECT b FROM BangPhanCong b WHERE b.ngayPhanCong = :ngayPhanCong and b.congDoan.tenCongDoan = :tenCongDoan",
				BangPhanCong.class).setParameter("ngayPhanCong", date).setParameter("tenCongDoan", tenCongDoan)
				.getResultList();
	}

	@Override
	public boolean themBangChamCong(BangChamCongThoLamDan bangChamCong) throws RemoteException {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.merge(bangChamCong);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BangChamCongThoLamDan> layDanhSachChamCong(Date date) throws RemoteException {
		return em.createQuery("SELECT b FROM BangChamCongThoLamDan b WHERE b.ngayChamCong = :ngay",
				BangChamCongThoLamDan.class).setParameter("ngay", date).getResultList();
	}

	@Override
	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTen(Date date, String ten) throws RemoteException {
		return em.createQuery(
				"SELECT b FROM BangChamCongThoLamDan b WHERE b.ngayChamCong = :ngay and b.bangPhanCong.thoLamDan.hoTen like :ten",
				BangChamCongThoLamDan.class).setParameter("ngay", date).setParameter("ten", "%" + ten + "%")
				.getResultList();
	}

	@Override
	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoSP(Date date, String maSP) throws RemoteException {
		return em.createQuery(
				"SELECT b FROM BangChamCongThoLamDan b WHERE b.ngayChamCong = :ngay and b.bangPhanCong.congDoan.dan.maSanPham = :maSP",
				BangChamCongThoLamDan.class).setParameter("ngay", date).setParameter("maSP", maSP).getResultList();
	}

	@Override
	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTenCongDoan(Date date, String tenCongDoan)
			throws RemoteException {
		return em.createQuery(
				"SELECT b FROM BangChamCongThoLamDan b WHERE b.ngayChamCong = :ngay and b.bangPhanCong.congDoan.tenCongDoan = :tenCongDoan",
				BangChamCongThoLamDan.class).setParameter("ngay", date).setParameter("tenCongDoan", tenCongDoan)
				.getResultList();
	}

	@Override
	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException {
		Long count = em.createQuery("SELECT COUNT(tld.maCongNhanVien) FROM ThoLamDan tld WHERE tld.hoTen LIKE :ten",
				Long.class).setParameter("ten", "%" + ten + "%").getSingleResult();

		return count.intValue();
	}

	public List<Object[]> laySoLuongLamDuocCuaThang(String maThoLamDan, int thang, int nam) throws RemoteException {
		String jpql = "SELECT TLD.maCongNhanVien, CD.maSanPham, CD.maCongDoan, SUM(BCCTLD.soLuongSanPham) \r\n"
				+ "FROM BangChamCongThoLamDan BCCTLD \r\n"
				+ "JOIN ThoLamDan TLD ON BCCTLD.maCongNhanVien = TLD.maCongNhanVien\r\n"
				+ "JOIN CongDoan CD ON BCCTLD.maCongDoan = CD.maCongDoan \r\n"
				+ "WHERE TLD.maCongNhanVien = :maCongNhanVien\r\n" + "AND Month (BCCTLD.ngayChamCong) = :thang\r\n"
				+ "AND YEAR(BCCTLD.ngayChamCong) = :nam\r\n"
				+ "GROUP BY TLD.maCongNhanVien, CD.maSanPham, CD.maCongDoan";

		Query query = em.createNativeQuery(jpql);
		query.setParameter("maCongNhanVien", maThoLamDan);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);

		return query.getResultList();

	}

}
