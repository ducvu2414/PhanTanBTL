package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

import entity.BangChamCongNhanVien;
import idao.I_DAO_ChamCongNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_ChamCongNhanVien extends UnicastRemoteObject implements I_DAO_ChamCongNhanVien {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3002889228729173001L;
	EntityManager em;

	public DAO_ChamCongNhanVien() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean themBangChamCong(BangChamCongNhanVien bangChamCong) throws RemoteException {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(bangChamCong);
			entityTransaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

	@Override
	public boolean KiemTraTrung(BangChamCongNhanVien bangChamCong) throws RemoteException {
		Long count = em.createQuery(
				"SELECT COUNT(bcc) FROM BangChamCongNhanVien bcc WHERE bcc.nhanVien.maCongNhanVien = :maNhanVien AND bcc.ngayChamCong = :ngayChamCong",
				Long.class).setParameter("maNhanVien", bangChamCong.getNhanVien().getMaCongNhanVien())
				.setParameter("ngayChamCong", bangChamCong.getNgayChamCong()).getSingleResult();

		return count != 0;
	}

	@Override
	public int kiemTraSoLuongDaChamCong(Date date) throws RemoteException {

		Long count = em
				.createQuery("SELECT COUNT(bcc.maBCCNhanVien) FROM BangChamCongNhanVien bcc "
						+ "WHERE FUNCTION('DAY', bcc.ngayChamCong) = :day "
						+ "AND FUNCTION('MONTH', bcc.ngayChamCong) = :month "
						+ "AND FUNCTION('YEAR', bcc.ngayChamCong) = :year", Long.class)
				.setParameter("day", date.toLocalDate().getDayOfMonth())
				.setParameter("month", date.toLocalDate().getMonthValue())
				.setParameter("year", date.toLocalDate().getYear()).getSingleResult();

		return count.intValue();
	}

	@Override
	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException {
		Long count = em
				.createQuery("SELECT COUNT(nv.maCongNhanVien) FROM NhanVien nv WHERE nv.hoTen LIKE :ten", Long.class)
				.setParameter("ten", "%" + ten + "%").getSingleResult();

		return count.intValue();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCong(Date date) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date",
				BangChamCongNhanVien.class).setParameter("date", date).getResultList();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCongTheoTen(Date date, String ten) throws RemoteException {
		return em.createQuery(
				"SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date AND b.nhanVien.hoTen like :ten",
				BangChamCongNhanVien.class).setParameter("date", date).setParameter("ten", "%" + ten + "%")
				.getResultList();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCongTheoPhongBan(Date date, String maPhongBan)
			throws RemoteException {

		return em.createQuery(
				"SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date AND b.nhanVien.phongBan.maPhongBan = :maPhongBan",
				BangChamCongNhanVien.class).setParameter("date", date).setParameter("maPhongBan", maPhongBan)
				.getResultList();
	}

}
