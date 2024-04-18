package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.CongNhanVien;

public interface I_DAO_CongNhanVien extends Remote {
	public CongNhanVien getCongNhanVienMoiTao() throws RemoteException;

	public boolean isDuplicate(String hoTen, String maCanCuocCongDan, String soDienThoai) throws RemoteException;

	public CongNhanVien getCongNhanVienTheoMa(String maCongNhanVien) throws RemoteException;
}
