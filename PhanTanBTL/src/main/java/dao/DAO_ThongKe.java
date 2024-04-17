package dao;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import idao.I_DAO_ThongKe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class DAO_ThongKe implements I_DAO_ThongKe {

	private EntityManager em;

	public DAO_ThongKe() {
		em = Persistence.createEntityManagerFactory("MSSQL").createEntityManager();
	}

	@Override
	public List<Object[]> getTopLuongNhanVien(int thang, int nam) {
		String sql = "SELECT Top 5\r\n" + "cnv.hoTen,\r\n"
				+ "dbo.tinhPhuCapThamNien(cnv.ngayVaoLam, dbo.tinhHeSoLuongTLD(tho.tayNghe)) AS phuCapThamNien,\r\n"
				+ "SUM(cd.giaCongDoan * bcc.soLuongSanPham) AS luongThucTe,\r\n"
				+ "SUM(bcc.soLuongSanPham) AS tongSoSP,\r\n"
				+ "SUM(cd.giaCongDoan * bcc.soLuongSanPham) + dbo.tinhPhuCapThamNien(cnv.ngayVaoLam, dbo.tinhHeSoLuongTLD(tho.tayNghe)) + 900000- (3000000 * 0.08 + 3000000 * 0.015 + 3000000 * 0.01) AS Tong\r\n"
				+ "FROM BangChamCongThoLamDan bcc \r\n" + "JOIN CongDoan cd ON bcc.maCongDoan = cd.maCongDoan\r\n"
				+ "JOIN ThoLamDan tho ON bcc.maCongNhanVien = tho.maCongNhanVien\r\n"
				+ "JOIN CongNhanVien cnv ON cnv.maCongNhanVien = tho.maCongNhanVien\r\n"
				+ "where MONTH(bcc.ngayChamCong) =  :thang and  YEAR(bcc.ngayChamCong)=  :nam\r\n" + "GROUP BY \r\n"
				+ "cnv.hoTen, cnv.ngayVaoLam, tho.tayNghe\r\n" + "ORDER BY luongThucTe DESC;";

		Query query = em.createNativeQuery(sql);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);

		return query.getResultList();
	}

	@Override
	public DefaultTableModel getSPTheoTopLuongNhanVien(int thang, int nam) {
		return null;
	}

	public DefaultTableModel allMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ ") AS innerQuery " + "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel allMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ ") AS innerQuery \r\n" + "ORDER BY luongThucLinh ASC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongKinhDoanhMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Kinh Doanh");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongKinhDoanhMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Kinh Doanh");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongMarketingMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Marketing");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongMarketingMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh ASC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Marketing");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongNhanSuMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Nhân Sự");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();

				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongNhanSuMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh ASC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Nhân Sự");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongPhatTrienSanPhamMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESV\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Phát Triển Sản Phẩm");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongPhatTrienSanPhamMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh ASC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Phát Triển Sản Phẩm");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongDieuPhoiMax(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh DESC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Điều Phối");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

	@Override
	public DefaultTableModel phongDieuPhoiMin(int thang, int nam) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Mã số");
		model.addColumn("Họ Tên");
		model.addColumn("Giới tính");
		model.addColumn("Phòng Ban");
		model.addColumn("Mã CCCD");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Tổng lương");

		try {
			String jpql = "SELECT\r\n" + "innerQuery.maCongNhanVien,\r\n" + "innerQuery.hoTen, \r\n"
					+ "innerQuery.gioiTinh,\r\n" + "innerQuery.tenPhongBan,\r\n" + "innerQuery.maCanCuocCongDan, \r\n"
					+ "innerQuery.soDienThoai,\r\n"
					+ "innerQuery.tongLuong * 0.895 + innerQuery.phuCapThamNien AS luongThucLinh\r\n" + "FROM (\r\n"
					+ "    SELECT TOP 5\r\n" + "        NV.maCongNhanVien,\r\n" + "		cnv.hoTen, \r\n"
					+ "		cnv.gioiTinh,\r\n" + "		pb.tenPhongBan,\r\n" + "		cnv.maCanCuocCongDan, \r\n"
					+ "		cnv.soDienThoai,\r\n" + "        NV.luongCoBan,\r\n"
					+ "        NV.luongCoBan + (BLN.soNgayThuongDiLam * NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaNgayThuong * 25000\r\n"
					+ "            + BLN.soNgayLamChuNhat * 1.5 * (NV.luongCoBan * HSLNV.HSL) / 26\r\n"
					+ "            + BLN.soGioTangCaChuNhat * 25000 * 1.5 AS tongLuong,\r\n"
					+ "        HSLNV.HSL AS HeSoLuong,\r\n" + "        PCTNNV.PCTNNV AS phuCapThamNien\r\n"
					+ "    FROM\r\n" + "        NhanVien NV\r\n"
					+ "        JOIN CongNhanVien CNV ON NV.maCongNhanVien = CNV.maCongNhanVien\r\n"
					+ "		Join PhongBan pb on NV.maPhongBan= pb.maPhongBan\r\n"
					+ "        JOIN BangLuongNhanVien BLN ON NV.maCongNhanVien = BLN.maCongNhanVien\r\n"
					+ "        JOIN BangChamCongNhanVien BCCN ON NV.maCongNhanVien = BCCN.maCongNhanVien\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhHeSoLuong(CNV.ngayVaoLam, NV.trinhDoVanHoa) AS HSL) AS HSLNV\r\n"
					+ "    CROSS APPLY\r\n"
					+ "        (SELECT dbo.tinhPhuCapThamNienNhanVien(NV.luongCoBan, CNV.ngayVaoLam, HSLNV.HSL) AS PCTNNV) AS PCTNNV\r\n"
					+ "    WHERE\r\n" + "        BLN.thang = :thang AND\r\n" + "        BLN.nam = :nam\r\n"
					+ "        and pb.tenPhongBan = :tenPhongBan\r\n" + ") AS innerQuery \r\n"
					+ "ORDER BY luongThucLinh ASC\r\n";
			Query query = em.createNativeQuery(jpql, Object[].class);
			query.setParameter("thang", thang);
			query.setParameter("nam", nam);
			query.setParameter("tenPhongBan", "Điều Phối");
			query.setMaxResults(5);

			List<Object[]> results = query.getResultList();
			for (Object[] result : results) {
				Vector<Object> row = new Vector<>();
				row.add(result[0]);
				row.add(result[1]);
				row.add(result[2]);
				row.add(result[3]);
				row.add(result[4]);
				row.add(result[5]);
				row.add(result[6]);
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return model;
	}

}
