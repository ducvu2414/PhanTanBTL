package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Dan;

public interface I_DAO_SanPham extends Remote {
	public List<Dan> docTuBang() throws RemoteException;

	public boolean taoSP(Dan dan) throws RemoteException;

	public String getMaSanPhamMoiTao() throws RemoteException;

	public boolean update(Dan dan) throws RemoteException;
}
