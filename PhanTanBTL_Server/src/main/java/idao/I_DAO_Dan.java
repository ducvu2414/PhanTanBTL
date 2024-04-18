package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Dan;

public interface I_DAO_Dan extends Remote {
	public List<Dan> getAlListDan() throws RemoteException;

	public Dan getDanTheoMaSanPham(String maSanPham) throws RemoteException;

	public Dan getDanTheoTenSanPham(String tenSanPham) throws RemoteException;

	public List<Dan> searchDanTheoLoaiSP(String loaiSanPham) throws RemoteException;
}
