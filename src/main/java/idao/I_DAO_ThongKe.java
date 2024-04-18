package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public interface I_DAO_ThongKe extends Remote {
	public List<Object[]> getTopLuongNhanVien(int thang, int nam) throws RemoteException;

	public DefaultTableModel allMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel allMin(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongKinhDoanhMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongKinhDoanhMin(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongMarketingMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongMarketingMin(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongNhanSuMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongNhanSuMin(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongPhatTrienSanPhamMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongPhatTrienSanPhamMin(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongDieuPhoiMax(int thang, int nam) throws RemoteException;

	public DefaultTableModel phongDieuPhoiMin(int thang, int nam) throws RemoteException;
}
