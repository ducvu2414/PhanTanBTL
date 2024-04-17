package dao;

import java.util.List;

import entity.Dan;
import idao.I_DAO_Dan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_Dan implements I_DAO_Dan {
	private EntityManager em;

	public DAO_Dan() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<Dan> getAlListDan() {
		return em.createQuery("Select d from Dan d", Dan.class).getResultList();
	}

	@Override
	public Dan getDanTheoMaSanPham(String maSanPham) {
		// TODO Auto-generated method stub
		return em.find(Dan.class, maSanPham);
	}

	@Override
	public Dan getDanTheoTenSanPham(String tenSanPham) {
		return em.createQuery("select d from Dan d where d.tenSanPham = :tenSanPham", Dan.class)
				.setParameter("tenSanPham", tenSanPham).getSingleResult();
	}

	@Override
	public List<Dan> searchDanTheoLoaiSP(String loaiSanPham) {
		return em.createQuery("select d from Dan d where d.loaiSanPham = :loaiSanPham", Dan.class)
				.setParameter("loaiSanPham", loaiSanPham).getResultList();
	}

}
