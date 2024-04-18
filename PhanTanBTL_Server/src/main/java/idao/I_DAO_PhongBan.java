package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.PhongBan;

public interface I_DAO_PhongBan extends Remote {

	public List<PhongBan> docTuBang() throws RemoteException;

	public PhongBan getPhongBanTheoTen(String tenPhongBan) throws RemoteException;

	public PhongBan getPhongBanTheoMa(String maPhongBan) throws RemoteException;

	public List<PhongBan> getTatCaPhongBan() throws RemoteException;
}
