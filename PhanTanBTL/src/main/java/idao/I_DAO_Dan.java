package idao;

import java.util.List;

import entity.Dan;

public interface I_DAO_Dan {
	public List<Dan> getAlListDan();

	public Dan getDanTheoMaSanPham(String maSanPham);

	public Dan getDanTheoTenSanPham(String tenSanPham);

	public List<Dan> searchDanTheoLoaiSP(String loaiSanPham);
}
