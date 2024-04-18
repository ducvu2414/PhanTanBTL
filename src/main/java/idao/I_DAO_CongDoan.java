package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.CongDoan;

public interface I_DAO_CongDoan extends Remote {
	public boolean insertCongDoan(CongDoan congDoan) throws RemoteException;

	public List<CongDoan> getAlListCongDoan() throws RemoteException;

	public boolean updateCongDoan(CongDoan congDoan) throws RemoteException;

	public CongDoan getCongDoanTheoMaCongDoan(String maCongDoan) throws RemoteException;

	public String getMaCongDoan(String maSanPham) throws RemoteException;

	public CongDoan getCongDoanTheoCDSP(String tenCongDoan, String maSanPham) throws RemoteException;

	public List<Object[]> getCongDoanTheoTLDNgayThang(String maThoLamDan, int thang, int nam) throws RemoteException;

	public boolean checkIfExists(String tenCongDoan, String maSanPham) throws RemoteException;
}
