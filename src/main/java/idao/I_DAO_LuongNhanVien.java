package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.BangLuongNhanVien;

public interface I_DAO_LuongNhanVien extends Remote {
	public boolean themBangLuongNhanVien(BangLuongNhanVien bangLuong) throws RemoteException;

	public boolean updateBangLuongNhanVien(BangLuongNhanVien bangLuong) throws RemoteException;

	public int laySoNgayDiLamNguyenCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoNgayDiLamNuaCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoGioTangCaNgayThuong(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoNgayDiLamNguyenCaCN(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoNgayDiLamNuaCaCN(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoGioTangCaCN(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoNgayNghiCoPhep(String maNhanVien, int thang, int nam) throws RemoteException;

	public int laySoNgayNghiKhongPhep(String maNhanVien, int thang, int nam) throws RemoteException;

	public String getMaBangLuong(int thang, int nam, String maNhanVien) throws RemoteException;

	public boolean kiemTraTrungMa(int thang, int nam, String maNhanVien) throws RemoteException;

	public BangLuongNhanVien getBangLuongTheoMa(String maBangLuong) throws RemoteException;

	public int kiemTraTimKiemTheoTen(String ten) throws RemoteException;

	public List<BangLuongNhanVien> getBangLuongTheoTen(String tenNhanVien, int thang, int nam) throws RemoteException;

	public List<BangLuongNhanVien> getBangLuongTheoPhongBan(String maPhongBan, int thang, int nam)
			throws RemoteException;
}
