package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.BangChamCongThoLamDan;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BangChamCongThoLamDanIdGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4585165759817389216L;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		BangChamCongThoLamDan bangChamCong = (BangChamCongThoLamDan) object;

		String maThoLamDan = bangChamCong.getBangPhanCong().getThoLamDan().getMaCongNhanVien().substring(2);
		Date ngay = bangChamCong.getNgayChamCong();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dateFormat.format(ngay);

		return "BCCTLD" + maThoLamDan + dateStr;
	}
}
