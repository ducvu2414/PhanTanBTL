package idao;

import java.sql.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import entity.BangPhanCong;

public interface I_DAO_BangPhanCong {
	public List<BangPhanCong> getAlListBangPhanCong();

	public void insertBangPhanCong(BangPhanCong bangPhanCong);

	public BangPhanCong getBangPhanCongMoiTao();

	public List<BangPhanCong> getBangPhanCongTheoNgayPhanCong(JDateChooser ngayPhanCong);

	public BangPhanCong getBangPhanCongTheoNgayTLDSP(Date ngay, String maTLD, String maCongDoan);

	public BangPhanCong getBangPhanCongTheoNgayTLD(Date ngay, String maTLD);
}
