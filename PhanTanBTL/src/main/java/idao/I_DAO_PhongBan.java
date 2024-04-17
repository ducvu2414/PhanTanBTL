package idao;

import java.util.List;

import entity.PhongBan;

public interface I_DAO_PhongBan {

	public List<PhongBan> docTuBang();

	public PhongBan getPhongBanTheoTen(String tenPhongBan);

	public PhongBan getPhongBanTheoMa(String maPhongBan);

	public List<PhongBan> getTatCaPhongBan();
}
