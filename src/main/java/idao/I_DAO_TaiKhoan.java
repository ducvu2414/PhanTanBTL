package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.TaiKhoan;

public interface I_DAO_TaiKhoan extends Remote {
	public boolean themTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

	public boolean xoaTaiKhoan(String maTaiKhoan) throws RemoteException;

	public boolean kiemTraTaiKhoan(String maTK) throws RemoteException;

	public List<TaiKhoan> getDanhSachTaiKhoan() throws RemoteException;

	public TaiKhoan getTaiKhoanTheoMaTaiKhoan(String maTK) throws RemoteException;

	public TaiKhoan getMatKhauTheoMaNhanVien(String ma) throws RemoteException;
}
