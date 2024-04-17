package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MSSQL");
		EntityManager em = emf.createEntityManager();
		List<Object[]> list = em.createNativeQuery(
				"select top 5 nv.maCongNhanVien, cnv.hoTen, cnv.gioiTinh, pb.tenPhongBan,cnv.maCanCuocCongDan, cnv.soDienThoai, \r\n"
						+ "(nv.luongCoBan+(blnv.soNgayLamChuNhat + blnv.soNgayThuongDiLam)*30000+blnv.soGioTangCaChuNhat*1.5*25000+blnv.soGioTangCaNgayThuong*25000) as tongLuong\r\n"
						+ "from NhanVien nv join CongNhanVien cnv\r\n" + "on cnv.maCongNhanVien=nv.maCongNhanVien\r\n"
						+ "join BangLuongNhanVien blnv\r\n" + "on blnv.maCongNhanVien=nv.maCongNhanVien\r\n"
						+ "join PhongBan pb on pb.maPhongBan=nv.maPhongBan\r\n" + "where thang=:thang and nam=:nam \r\n"
						+ "order by tongLuong desc;\r\n" + "\r\n" + "\r\n" + "")
				.setParameter("thang", 4).setParameter("nam", 2024).getResultList();
		for (Object[] objects : list) {
			System.out.println(objects[0] + " - " + objects[1] + " - " + objects[2] + " - " + objects[3] + " - "
					+ objects[4] + " - " + objects[5] + " - " + objects[6]);
		}
		emf.close();
	}

}
