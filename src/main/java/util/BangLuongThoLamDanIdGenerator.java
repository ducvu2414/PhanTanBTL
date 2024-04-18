package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.BangLuongThoLamDan;

import java.io.Serializable;

public class BangLuongThoLamDanIdGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4340845016859152046L;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		BangLuongThoLamDan bangLuong = (BangLuongThoLamDan) object;
		String maNhanVien = bangLuong.getThoLamDan().getMaCongNhanVien().substring(2);
		int thang = bangLuong.getThang();
		int nam = bangLuong.getNam();

		String baseId = String.format("BLTLD%s%02d%04d", maNhanVien, thang, nam);

		return baseId;
	}
}
