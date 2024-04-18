package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.PhongBan;
import idao.I_DAO_PhongBan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_PhongBan extends UnicastRemoteObject implements I_DAO_PhongBan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8334438378538609577L;
	private EntityManager em;

	public DAO_PhongBan() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<PhongBan> docTuBang() throws RemoteException {
		return em.createQuery("Select p from PhongBan p", PhongBan.class).getResultList();
	}

	@Override
	public PhongBan getPhongBanTheoTen(String tenPhongBan) throws RemoteException {
		return em.createQuery("select p from PhongBan p where p.tenPhongBan = :tenPhongBan", PhongBan.class)
				.setParameter("tenPhongBan", tenPhongBan).getSingleResult();
	}

	@Override
	public PhongBan getPhongBanTheoMa(String maPhongBan) throws RemoteException {
		return em.find(PhongBan.class, maPhongBan);
	}

	@Override
	public List<PhongBan> getTatCaPhongBan() throws RemoteException {
		return em.createQuery("select p from PhongBan p", PhongBan.class).getResultList();
	}

}
