package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import entity.BangChamCongThoLamDan;
import entity.BangPhanCong;

public interface I_DAO_ChamCongThoLamDan extends Remote {

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTai() throws RemoteException;

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaSP(String maSP) throws RemoteException;

	public List<BangPhanCong> listAllBangPhanCongTheoNgayHienTaiVaTenCongDoan(String tenCongDoan)
			throws RemoteException;

	public boolean themBangChamCong(BangChamCongThoLamDan bangChamCong) throws RemoteException;

	public List<BangChamCongThoLamDan> layDanhSachChamCong(Date date) throws RemoteException;

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTen(Date date, String ten) throws RemoteException;

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoSP(Date date, String maSP) throws RemoteException;

	public List<BangChamCongThoLamDan> layDanhSachChamCongTheoTenCongDoan(Date date, String tenCongDoan)
			throws RemoteException;

	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException;

	public List<Object[]> laySoLuongLamDuocCuaThang(String maThoLamDan, int thang, int nam) throws RemoteException;
}
