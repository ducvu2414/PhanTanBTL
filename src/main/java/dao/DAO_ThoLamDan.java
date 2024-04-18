package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.GioiTinh;
import entity.ThoLamDan;
import idao.I_DAO_ThoLamDan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO_ThoLamDan extends UnicastRemoteObject implements I_DAO_ThoLamDan {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6836403852253373577L;
	private EntityManager em;

	public DAO_ThoLamDan() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public boolean taoTLD(ThoLamDan tld) throws RemoteException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(tld);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String getMaThoLamDanMoiTao() throws RemoteException {

		return em.createNamedQuery("ThoLamDan.getMaThoLamDanMoiTao", String.class).getSingleResult();
	}

	@Override
	public List<ThoLamDan> getAlListThoLamDan() throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getAlListThoLamDan", ThoLamDan.class).getResultList();
	}

	@Override
	public ThoLamDan getTLDTheoMaThoLamDan(String maThoLamDan) throws RemoteException {
		return em.find(ThoLamDan.class, maThoLamDan);
	}

	@Override
	public List<ThoLamDan> getAlListThoLamDanTheoTen(String ten) throws RemoteException {

		return em.createNamedQuery("ThoLamDan.getAlListThoLamDanTheoTen", ThoLamDan.class)
				.setParameter("hoTen", "%" + ten + "%").getResultList();
	}

	@Override
	public List<ThoLamDan> getThoLamDanTheoTen(String ten) throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getThoLamDanTheoTen", ThoLamDan.class).setParameter("hoTen", ten)
				.getResultList();
	}

	@Override
	public List<ThoLamDan> getThoLamDanTheoTrangThai(boolean trangThai) throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getThoLamDanTheoTrangThai", ThoLamDan.class)
				.setParameter("trangThai", trangThai).getResultList();
	}

	@Override
	public List<ThoLamDan> getAllThoLamDanTheoSoDienThoai(String sdt) throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getAllThoLamDanTheoSoDienThoai", ThoLamDan.class)
				.setParameter("soDienThoai", sdt).getResultList();
	}

	@Override
	public List<ThoLamDan> getThoLamDanTheoGioiTinh(GioiTinh gioiTinh) throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getThoLamDanTheoGioiTinh", ThoLamDan.class)
				.setParameter("gioiTinh", gioiTinh).getResultList();
	}

	@Override
	public List<ThoLamDan> getThoLamDanTheoDiaChi(String diaChi) throws RemoteException {
		return em.createNamedQuery("ThoLamDan.getThoLamDanTheoDiaChi", ThoLamDan.class).setParameter("diaChi", diaChi)
				.getResultList();
	}

	@Override
	public boolean update(ThoLamDan tld) throws RemoteException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(tld);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<ThoLamDan> docTuBang() throws RemoteException {
		return em.createNamedQuery("ThoLamDan.docTubang", ThoLamDan.class).getResultList();
	}

}
