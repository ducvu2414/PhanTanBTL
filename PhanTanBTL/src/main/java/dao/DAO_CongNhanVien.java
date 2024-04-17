package dao;

import entity.CongNhanVien;
import idao.I_DAO_CongNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_CongNhanVien implements I_DAO_CongNhanVien {
	private EntityManager em;

	public DAO_CongNhanVien() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public CongNhanVien getCongNhanVienMoiTao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicate(String hoTen, String maCanCuocCongDan, String soDienThoai) {

		return em.createQuery(
				"SELECT COUNT(c) FROM CongNhanVien c WHERE c.hoTen = :hoTen OR c.maCanCuocCongDan = :maCanCuocCongDan OR c.soDienThoai = :soDienThoai",
				Long.class).setParameter("hoTen", hoTen).setParameter("maCanCuocCongDan", maCanCuocCongDan)
				.setParameter("soDienThoai", soDienThoai).getSingleResult() > 0;
	}

	@Override
	public CongNhanVien getCongNhanVienTheoMa(String maCongNhanVien) {
		// TODO Auto-generated method stub
		return null;
	}

}
