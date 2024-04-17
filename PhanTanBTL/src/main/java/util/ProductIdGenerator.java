package util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;

public class ProductIdGenerator implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1307588969186702229L;
	private static final String PRODUCT_PREFIX = "SP";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		String queryString = "SELECT maSanPham FROM Dan WHERE maSanPham LIKE :prefix ORDER BY maSanPham DESC";
		@SuppressWarnings("deprecation")
		String maxMaSP = (String) session.createQuery(queryString).setParameter("prefix", PRODUCT_PREFIX + "%")
				.setMaxResults(1).uniqueResult();

		int nextIdNum = maxMaSP != null ? Integer.parseInt(maxMaSP.replace(PRODUCT_PREFIX, "")) + 1 : 1;

		return PRODUCT_PREFIX + String.format("%03d", nextIdNum);
	}
}
