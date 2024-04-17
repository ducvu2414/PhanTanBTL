package dao;

import java.sql.Date;
import java.util.List;

import entity.BangChamCongNhanVien;
import idao.I_DAO_ChamCongNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_ChamCongNhanVien implements I_DAO_ChamCongNhanVien {
	EntityManager em;

	public DAO_ChamCongNhanVien() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean themBangChamCong(BangChamCongNhanVien bangChamCong) {
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
	public boolean KiemTraTrung(BangChamCongNhanVien bangChamCong) {
		Long count = em.createQuery(
				"SELECT COUNT(bcc) FROM BangChamCongNhanVien bcc WHERE bcc.nhanVien.maCongNhanVien = :maNhanVien AND bcc.ngayChamCong = :ngayChamCong",
				Long.class).setParameter("maNhanVien", bangChamCong.getNhanVien().getMaCongNhanVien())
				.setParameter("ngayChamCong", bangChamCong.getNgayChamCong()).getSingleResult();

		return count != 0;
	}

	@Override
	public int kiemTraSoLuongDaChamCong(Date date) {

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
	public int kiemTraTimKiemTheoTen(String ten) {
		Long count = em
				.createQuery("SELECT COUNT(nv.maCongNhanVien) FROM NhanVien nv WHERE nv.hoTen LIKE :ten", Long.class)
				.setParameter("ten", "%" + ten + "%").getSingleResult();

		return count.intValue();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCong(Date date) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date",
				BangChamCongNhanVien.class).setParameter("date", date).getResultList();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCongTheoTen(Date date, String ten) {
		return em.createQuery(
				"SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date AND b.nhanVien.hoTen like :ten",
				BangChamCongNhanVien.class).setParameter("date", date).setParameter("ten", "%" + ten + "%")
				.getResultList();
	}

	@Override
	public List<BangChamCongNhanVien> layDanhSachChamCongTheoPhongBan(Date date, String maPhongBan) {

		return em.createQuery(
				"SELECT b FROM BangChamCongNhanVien b WHERE b.ngayChamCong = :date AND b.nhanVien.phongBan.maPhongBan = :maPhongBan",
				BangChamCongNhanVien.class).setParameter("date", date).setParameter("maPhongBan", maPhongBan)
				.getResultList();
	}

}
