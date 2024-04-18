package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.BangChamCongNhanVien;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BangChamCongNhanVienIdGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4585165759817389216L;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		BangChamCongNhanVien bangChamCong = (BangChamCongNhanVien) object;

		String maNhanVien = bangChamCong.getNhanVien().getMaCongNhanVien().substring(2);
		Date ngay = bangChamCong.getNgayChamCong();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dateFormat.format(ngay);

		return "BCCNV" + maNhanVien + dateStr;
	}
}
