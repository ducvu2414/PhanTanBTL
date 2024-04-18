package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.CongDoan;

import jakarta.persistence.Query;
import java.io.Serializable;

public class CongDoanIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		CongDoan congDoan = (CongDoan) object;
		String maSanPham = congDoan.getDan().getMaSanPham();

		String queryStr = "SELECT max(cd.maCongDoan) FROM CongDoan cd WHERE cd.dan.maSanPham = :maSanPham AND cd.maCongDoan LIKE :pattern";
		Query query = session.createQuery(queryStr);
		query.setParameter("maSanPham", maSanPham);
		query.setParameter("pattern", maSanPham + "CD%");

		String lastMaCongDoan = (String) query.getSingleResult();
		int nextId = lastMaCongDoan != null
				? Integer.parseInt(lastMaCongDoan.substring(lastMaCongDoan.length() - 3)) + 1
				: 1;

		return maSanPham + "CD" + String.format("%03d", nextId);
	}
}
