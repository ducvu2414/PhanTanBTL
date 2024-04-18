package idao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import entity.BangPhanCong;

public interface I_DAO_BangPhanCong extends Remote {
	public List<BangPhanCong> getAlListBangPhanCong() throws RemoteException;

	public void insertBangPhanCong(BangPhanCong bangPhanCong) throws RemoteException;

	public BangPhanCong getBangPhanCongMoiTao() throws RemoteException;

	public List<BangPhanCong> getBangPhanCongTheoNgayPhanCong(JDateChooser ngayPhanCong) throws RemoteException;

	public BangPhanCong getBangPhanCongTheoNgayTLDSP(Date ngay, String maTLD, String maCongDoan) throws RemoteException;

	public BangPhanCong getBangPhanCongTheoNgayTLD(Date ngay, String maTLD) throws RemoteException;
}
