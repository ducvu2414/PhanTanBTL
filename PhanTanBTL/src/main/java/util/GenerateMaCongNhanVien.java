package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import entity.ThoLamDan;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.stream.Stream;

public class GenerateMaCongNhanVien implements IdentifierGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5109801385820308787L;
	public static final String SEQUENCE_PREFIX = "sequence_prefix";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String prefix = object instanceof ThoLamDan ? "TLD" : "NV";
		String query = String.format("select %s from %s",
				session.getEntityPersister(object.getClass().getName(), object).getIdentifierPropertyName(),
				object.getClass().getSimpleName());

		@SuppressWarnings("unchecked")
		Stream<String> ids = session.createQuery(query).stream();
		Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);

		return prefix + new DecimalFormat("000").format(max + 1);
	}
}
