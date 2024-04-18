package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.GioiTinh;
import entity.ThoLamDan;

public interface I_DAO_ThoLamDan extends Remote {
	public List<ThoLamDan> docTuBang() throws RemoteException;

	public boolean taoTLD(ThoLamDan tld) throws RemoteException;

	public String getMaThoLamDanMoiTao() throws RemoteException;

	public List<ThoLamDan> getAlListThoLamDan() throws RemoteException;

	public ThoLamDan getTLDTheoMaThoLamDan(String maThoLamDan) throws RemoteException;

	public List<ThoLamDan> getAlListThoLamDanTheoTen(String ten) throws RemoteException;

	public List<ThoLamDan> getThoLamDanTheoTen(String ten) throws RemoteException;

	public List<ThoLamDan> getThoLamDanTheoTrangThai(boolean trangThai) throws RemoteException;

	public List<ThoLamDan> getAllThoLamDanTheoSoDienThoai(String sdt) throws RemoteException;

	public List<ThoLamDan> getThoLamDanTheoGioiTinh(GioiTinh gioiTinh) throws RemoteException;

	public List<ThoLamDan> getThoLamDanTheoDiaChi(String diaChi) throws RemoteException;

	public boolean update(ThoLamDan tld) throws RemoteException;
}
