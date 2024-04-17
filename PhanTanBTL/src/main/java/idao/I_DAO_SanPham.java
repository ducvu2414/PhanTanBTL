package idao;

import java.util.List;

import entity.Dan;

public interface I_DAO_SanPham {
	public List<Dan> docTuBang();

	public boolean taoSP(Dan dan);

	public String getMaSanPhamMoiTao();

	public boolean update(Dan dan);
}
