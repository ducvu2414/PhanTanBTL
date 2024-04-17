package idao;

import java.sql.ResultSet;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public interface I_DAO_ThongKe {
	public List<Object[]> getTopLuongNhanVien(int thang, int nam);

	public DefaultTableModel getSPTheoTopLuongNhanVien(int thang, int nam);

	public DefaultTableModel allMax(int thang, int nam);

	public DefaultTableModel allMin(int thang, int nam);

	public DefaultTableModel phongKinhDoanhMax(int thang, int nam);

	public DefaultTableModel phongKinhDoanhMin(int thang, int nam);

	public DefaultTableModel phongMarketingMax(int thang, int nam);

	public DefaultTableModel phongMarketingMin(int thang, int nam);

	public DefaultTableModel phongNhanSuMax(int thang, int nam);

	public DefaultTableModel phongNhanSuMin(int thang, int nam);

	public DefaultTableModel phongPhatTrienSanPhamMax(int thang, int nam);

	public DefaultTableModel phongPhatTrienSanPhamMin(int thang, int nam);

	public DefaultTableModel phongDieuPhoiMax(int thang, int nam);

	public DefaultTableModel phongDieuPhoiMin(int thang, int nam);
}
