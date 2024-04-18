package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.Dan;
import idao.I_DAO_Dan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_Dan extends UnicastRemoteObject implements I_DAO_Dan {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1939300254626689787L;
	private EntityManager em;

	public DAO_Dan() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<Dan> getAlListDan() throws RemoteException {
		return em.createQuery("Select d from Dan d", Dan.class).getResultList();
	}

	@Override
	public Dan getDanTheoMaSanPham(String maSanPham) throws RemoteException {
		// TODO Auto-generated method stub
		return em.find(Dan.class, maSanPham);
	}

	@Override
	public Dan getDanTheoTenSanPham(String tenSanPham) throws RemoteException {
		return em.createQuery("select d from Dan d where d.tenSanPham = :tenSanPham", Dan.class)
				.setParameter("tenSanPham", tenSanPham).getSingleResult();
	}

	@Override
	public List<Dan> searchDanTheoLoaiSP(String loaiSanPham) throws RemoteException {
		return em.createQuery("select d from Dan d where d.loaiSanPham = :loaiSanPham", Dan.class)
				.setParameter("loaiSanPham", loaiSanPham).getResultList();
	}

}
