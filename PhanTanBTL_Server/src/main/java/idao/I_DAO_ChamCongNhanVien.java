package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import entity.BangChamCongNhanVien;

public interface I_DAO_ChamCongNhanVien extends Remote {
	public boolean themBangChamCong(BangChamCongNhanVien bangChamCong) throws RemoteException;

	public boolean KiemTraTrung(BangChamCongNhanVien bangChamCong) throws RemoteException;

	public int kiemTraSoLuongDaChamCong(Date date) throws RemoteException;

	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException;

	public List<BangChamCongNhanVien> layDanhSachChamCong(Date date) throws RemoteException;

	public List<BangChamCongNhanVien> layDanhSachChamCongTheoTen(Date date, String ten) throws RemoteException;

	public List<BangChamCongNhanVien> layDanhSachChamCongTheoPhongBan(Date date, String maPhongBan)
			throws RemoteException;
}
