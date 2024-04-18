package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.BangLuongNhanVien;

import java.io.Serializable;

public class BangLuongNhanVienIdGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4340845016859152046L;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		BangLuongNhanVien bangLuong = (BangLuongNhanVien) object;
		String maNhanVien = bangLuong.getNhanVien().getMaCongNhanVien().substring(2);
		int thang = bangLuong.getThang();
		int nam = bangLuong.getNam();

		String baseId = String.format("BLNV%s%02d%04d", maNhanVien, thang, nam);

		return baseId;
	}
}
