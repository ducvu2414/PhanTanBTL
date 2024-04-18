package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.GioiTinh;
import entity.NhanVien;

public interface I_DAO_NhanVien extends Remote {
	public List<NhanVien> docTuBang() throws RemoteException;

	public boolean taoNV(NhanVien nv) throws RemoteException;

	public List<NhanVien> getAllListNhanVien() throws RemoteException;

	public NhanVien getNhanVienTheoMa(String maNhanVien) throws RemoteException;

	public String getMaNhanVienMoiTao() throws RemoteException;

	public List<NhanVien> getAllNhanVienTheoTen(String ten) throws RemoteException;

	public List<NhanVien> getAllNhanVienTheoPhongBan(String maPhongBan) throws RemoteException;

	public boolean update(NhanVien nv) throws RemoteException;

	public List<NhanVien> getNhanVienTheoTen(String ten) throws RemoteException;

	public List<NhanVien> getNhanVienTheoPhongBan(String maPhongBan) throws RemoteException;

	public List<NhanVien> getNhanVienTheoTrangThai(boolean trangThai) throws RemoteException;

	public List<NhanVien> getAllNhanVienTheoSoDienThoai(String sdt) throws RemoteException;

	public List<NhanVien> getNhanVienTheoGioiTinh(GioiTinh gioiTinh) throws RemoteException;

	public List<NhanVien> getAllNhanVienTheoDiaChi(String diaChi) throws RemoteException;
}
