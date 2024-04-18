package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import entity.CongNhanVien;
import idao.I_DAO_CongNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DAO_CongNhanVien extends UnicastRemoteObject implements I_DAO_CongNhanVien {
	/**
	 * 
	 */
	private static final long serialVersionUID = -186230116619079297L;
	private EntityManager em;

	public DAO_CongNhanVien() throws RemoteException {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public CongNhanVien getCongNhanVienMoiTao() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicate(String hoTen, String maCanCuocCongDan, String soDienThoai) throws RemoteException {

		return em.createQuery(
				"SELECT COUNT(c) FROM CongNhanVien c WHERE c.hoTen = :hoTen OR c.maCanCuocCongDan = :maCanCuocCongDan OR c.soDienThoai = :soDienThoai",
				Long.class).setParameter("hoTen", hoTen).setParameter("maCanCuocCongDan", maCanCuocCongDan)
				.setParameter("soDienThoai", soDienThoai).getSingleResult() > 0;
	}

	@Override
	public CongNhanVien getCongNhanVienTheoMa(String maCongNhanVien) throws RemoteException {
		// TODO Auto-generated method stub
		return em.find(CongNhanVien.class, maCongNhanVien);
	}

}
