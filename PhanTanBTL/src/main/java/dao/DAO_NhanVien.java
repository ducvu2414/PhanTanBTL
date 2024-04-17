package dao;

import java.util.List;

import entity.GioiTinh;
import entity.NhanVien;
import idao.I_DAO_NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_NhanVien implements I_DAO_NhanVien {
	EntityManager em;

	public DAO_NhanVien() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<NhanVien> docTuBang() {
		return em.createNamedQuery("NhanVien.docTubang", NhanVien.class).getResultList();
	}

	@Override
	public boolean taoNV(NhanVien nv) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(nv);
			entityTransaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;

	}

	@Override
	public List<NhanVien> getAllListNhanVien() {
		return em.createQuery("Select nv from NhanVien nv", NhanVien.class).getResultList();
	}

	@Override
	public NhanVien getNhanVienTheoMa(String maNhanVien) {
		return em.find(NhanVien.class, maNhanVien);
	}

	@Override
	public List<NhanVien> getAllNhanVienTheoTen(String ten) {
		return em.createQuery("select nv from NhanVien nv where nv.hoTen like :ten and nv.trangThai = true",
				NhanVien.class).setParameter("ten", "%" + ten + "%").getResultList();
	}

	@Override
	public List<NhanVien> getAllNhanVienTheoPhongBan(String maPhongBan) {
		return em.createQuery(
				"select nv from NhanVien nv where nv.phongBan.maPhongBan = :maPhongBan and nv.trangThai = true",
				NhanVien.class).setParameter("maPhongBan", maPhongBan).getResultList();
	}

	@Override
	public boolean update(NhanVien nv) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.merge(nv);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return false;
	}

	@Override
	public List<NhanVien> getNhanVienTheoTen(String ten) {
		return em.createQuery("select nv from NhanVien nv where nv.hoTen like :ten", NhanVien.class)
				.setParameter("ten", "%" + ten + "%").getResultList();
	}

	@Override
	public List<NhanVien> getNhanVienTheoPhongBan(String maPhongBan) {
		return em.createQuery("select nv from NhanVien nv where nv.phongBan.maPhongBan = :maPhongBan", NhanVien.class)
				.setParameter("maPhongBan", maPhongBan).getResultList();
	}

	@Override
	public List<NhanVien> getNhanVienTheoTrangThai(boolean trangThai) {
		return em.createQuery("select nv from NhanVien nv where nv.trangThai = :trangThai", NhanVien.class)
				.setParameter("trangThai", trangThai).getResultList();
	}

	@Override
	public List<NhanVien> getAllNhanVienTheoSoDienThoai(String sdt) {
		// TODO Auto-generated method stub
		return em.createQuery("select nv from NhanVien nv where nv.soDienThoai like :sdt", NhanVien.class)
				.setParameter("sdt", "%" + sdt + "%").getResultList();
	}

	@Override
	public List<NhanVien> getNhanVienTheoGioiTinh(GioiTinh gioiTinh) {
		// TODO Auto-generated method stub
		return em.createQuery("select nv from NhanVien nv where nv.gioiTinh = :gioiTinh", NhanVien.class)
				.setParameter("gioiTinh", gioiTinh).getResultList();
	}

	@Override
	public List<NhanVien> getAllNhanVienTheoDiaChi(String diaChi) {

		return em.createQuery("select nv from NhanVien nv where nv.diaChi like :diaChi", NhanVien.class)
				.setParameter("diaChi", "%" + diaChi + "%").getResultList();
	}

	@Override
	public String getMaNhanVienMoiTao() {

		return em.createQuery("select max(nv.maCongNhanVien) from NhanVien nv", String.class).getSingleResult();
	}

}
