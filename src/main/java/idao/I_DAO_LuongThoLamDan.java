package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.BangLuongThoLamDan;

public interface I_DAO_LuongThoLamDan extends Remote {
	public boolean themBangLuongThoLamDan(BangLuongThoLamDan bangLuong) throws RemoteException;

	public boolean updateBangLuongThoLamDan(BangLuongThoLamDan bangLuong) throws RemoteException;

	public String getMaBangLuong(int thang, int nam, String maThoLamDan) throws RemoteException;

	public boolean kiemTraTrungMa(int thang, int nam, String maThoLamDan) throws RemoteException;

	public BangLuongThoLamDan getBangLuongTheoMa(String maBangLuong) throws RemoteException;

	public int laySoSanPham(String maThoLamDan, int thang, int nam) throws RemoteException;

	public double layTongThuNhapTungThang(String maThoLamDan, int thang, int nam) throws RemoteException;

	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException;

	public List<BangLuongThoLamDan> getBangLuongTheoTen(String tenThoLamDan, int thang, int nam) throws RemoteException;

}
