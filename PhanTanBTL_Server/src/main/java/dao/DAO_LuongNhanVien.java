package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.BangLuongNhanVien;
import idao.I_DAO_LuongNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DAO_LuongNhanVien extends UnicastRemoteObject implements I_DAO_LuongNhanVien {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5310385489688726829L;
	EntityManager em;

	public DAO_LuongNhanVien() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean themBangLuongNhanVien(BangLuongNhanVien bangLuong) throws RemoteException {
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
	public boolean updateBangLuongNhanVien(BangLuongNhanVien bangLuong) throws RemoteException {
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
	public int laySoNgayDiLamNguyenCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNguyenCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) <> 1 \r\n"
				+ "  AND trangThaiDiLam = N'Làm nguyên ngày công';\r\n";
		return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult()).intValue();

	}

	@Override
	public int laySoNgayDiLamNuaCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNuaCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) <> 1 \r\n"
				+ "  AND trangThaiDiLam = N'Làm nửa ngày công';\r\n";
		return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult()).intValue();
	}

	@Override
	public int laySoGioTangCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT SUM(soGioTangCa) AS TongSoGioTangCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) <> 1 \r\n";

		if (em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult() == null) {
			return 0;
		} else
			return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien)
					.setParameter("thang", thang).setParameter("nam", nam).getSingleResult()).intValue();

	}

	@Override
	public int laySoNgayDiLamNguyenCaCN(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNguyenCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) = 1 \r\n"
				+ "  AND trangThaiDiLam = N'Làm nguyên ngày công';\r\n";
		return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult()).intValue();
	}

	@Override
	public int laySoNgayDiLamNuaCaCN(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNuaCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) = 1 \r\n" + "  AND trangThaiDiLam = N'Làm nửa ngày công';\r\n";

		return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult()).intValue();

	}

	@Override
	public int laySoGioTangCaCN(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SET DATEFIRST 7;\r\n" + "SELECT SUM(soGioTangCa) AS TongSoGioTangCa\r\n"
				+ "FROM BangChamCongNhanVien\r\n" + "WHERE maCongNhanVien = :maNhanVien"
				+ "  AND MONTH(ngayChamCong) = :thang " + "  AND YEAR(ngayChamCong) =  :nam"
				+ "  AND DATEPART(weekday, ngayChamCong) = 1 \r\n";
		if (em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult() == null) {
			return 0;
		} else
			return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien)
					.setParameter("thang", thang).setParameter("nam", nam).getSingleResult()).intValue();

	}

	@Override
	public int laySoNgayNghiCoPhep(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNuaCa\r\n" + "FROM BangChamCongNhanVien\r\n"
				+ "WHERE maCongNhanVien = :maNhanVien" + "  AND MONTH(ngayChamCong) = :thang "
				+ "  AND YEAR(ngayChamCong) =  :nam" + "  AND trangThaiDiLam = N'Nghỉ phép';\r\n";
		if (em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult() == null) {
			return 0;
		} else
			return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien)
					.setParameter("thang", thang).setParameter("nam", nam).getSingleResult()).intValue();
	}

	@Override
	public int laySoNgayNghiKhongPhep(String maNhanVien, int thang, int nam) throws RemoteException {
		String sql = "SELECT COUNT(DISTINCT ngayChamCong) AS SoNgayLamNuaCa\r\n" + "FROM BangChamCongNhanVien\r\n"
				+ "WHERE maCongNhanVien = :maNhanVien" + "  AND MONTH(ngayChamCong) = :thang "
				+ "  AND YEAR(ngayChamCong) =  :nam" + "  AND trangThaiDiLam = N'Nghỉ không phép';\r\n";
		if (em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien).setParameter("thang", thang)
				.setParameter("nam", nam).getSingleResult() == null) {
			return 0;
		} else
			return ((Number) em.createNativeQuery(sql).setParameter("maNhanVien", maNhanVien)
					.setParameter("thang", thang).setParameter("nam", nam).getSingleResult()).intValue();

	}

	@Override
	public String getMaBangLuong(int thang, int nam, String maNhanVien) throws RemoteException {
		String jpql = "SELECT blnv.maBangLuong " + "FROM BangLuongNhanVien blnv " + "WHERE thang = :thang "
				+ "AND nam = :nam " + "AND blnv.nhanVien.maCongNhanVien = :maNhanVien";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		query.setParameter("maNhanVien", maNhanVien);
		return query.getSingleResult();
	}

	@Override
	public boolean kiemTraTrungMa(int thang, int nam, String maNhanVien) throws RemoteException {
		String jpql = "SELECT COUNT(blnv.maBangLuong) " + "FROM BangLuongNhanVien blnv " + "WHERE thang = :thang "
				+ "AND nam = :nam " + "AND blnv.nhanVien.maCongNhanVien = :maNhanVien";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		query.setParameter("maNhanVien", maNhanVien);
		int count = query.getSingleResult().intValue();
		return count != 0 ? true : false;
	}

	@Override
	public BangLuongNhanVien getBangLuongTheoMa(String maBangLuong) throws RemoteException {
		return em.find(BangLuongNhanVien.class, maBangLuong);
	}

	@Override
	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException {
		Long kt = em
				.createQuery("SELECT COUNT(nv.maCongNhanVien) FROM NhanVien nv where nv.hoTen LIKE :ten", Long.class)
				.setParameter("ten", "%" + ten + "%").getSingleResult();
		return kt.intValue();
	}

	@Override
	public List<BangLuongNhanVien> getBangLuongTheoTen(String tenNhanVien, int thang, int nam) throws RemoteException {

		return em
				.createQuery("SELECT blnv FROM BangLuongNhanVien blnv WHERE blnv.nhanVien.hoTen like :tenNhanVien "
						+ "AND thang = :thang " + "AND nam = :nam", BangLuongNhanVien.class)
				.setParameter("tenNhanVien", "%" + tenNhanVien + "%").setParameter("thang", thang)
				.setParameter("nam", nam).getResultList();
	}

	@Override
	public List<BangLuongNhanVien> getBangLuongTheoPhongBan(String maPhongBan, int thang, int nam)
			throws RemoteException {
		return em
				.createQuery(
						"SELECT blnv FROM BangLuongNhanVien blnv WHERE blnv.nhanVien.phongBan.maPhongBan = :maPhongBan "
								+ "AND thang = :thang " + "AND nam = :nam",
						BangLuongNhanVien.class)
				.setParameter("maPhongBan", maPhongBan).setParameter("thang", thang).setParameter("nam", nam)
				.getResultList();
	}

}
