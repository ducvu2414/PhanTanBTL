package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dao.DAO_BangPhanCong;
import dao.DAO_ChamCongNhanVien;
import dao.DAO_ChamCongThoLamDan;
import dao.DAO_CongDoan;
import dao.DAO_CongNhanVien;
import dao.DAO_Dan;
import dao.DAO_LuongNhanVien;
import dao.DAO_LuongThoLamDan;
import dao.DAO_NhanVien;
import dao.DAO_PhongBan;
import dao.DAO_SanPham;
import dao.DAO_TaiKhoan;
import dao.DAO_ThoLamDan;
import dao.DAO_ThongKe;
import idao.I_DAO_BangPhanCong;
import idao.I_DAO_ChamCongNhanVien;
import idao.I_DAO_ChamCongThoLamDan;
import idao.I_DAO_CongDoan;
import idao.I_DAO_CongNhanVien;
import idao.I_DAO_Dan;
import idao.I_DAO_LuongNhanVien;
import idao.I_DAO_LuongThoLamDan;
import idao.I_DAO_NhanVien;
import idao.I_DAO_PhongBan;
import idao.I_DAO_SanPham;
import idao.I_DAO_TaiKhoan;
import idao.I_DAO_ThoLamDan;
import idao.I_DAO_ThongKe;

public class Server {
	private static String URL = "rmi://DESKTOP-UH6CFJP:5511/";

	public static void main(String[] args) throws NamingException, RemoteException {
		Context context = new InitialContext();
		I_DAO_BangPhanCong i_DAO_BangPhanCong = new DAO_BangPhanCong();
		I_DAO_ChamCongNhanVien i_DAO_ChamCongNhanVien = new DAO_ChamCongNhanVien();
		I_DAO_ChamCongThoLamDan i_DAO_ChamCongThoLamDan = new DAO_ChamCongThoLamDan();
		I_DAO_CongDoan i_DAO_CongDoan = new DAO_CongDoan();
		I_DAO_CongNhanVien i_DAO_CongNhanVien = new DAO_CongNhanVien();
		I_DAO_Dan i_DAO_Dan = new DAO_Dan();
		I_DAO_LuongNhanVien i_DAO_LuongNhanVien = new DAO_LuongNhanVien();
		I_DAO_LuongThoLamDan i_DAO_LuongThoLamDan = new DAO_LuongThoLamDan();
		I_DAO_NhanVien i_DAO_NhanVien = new DAO_NhanVien();
		I_DAO_PhongBan i_DAO_PhongBan = new DAO_PhongBan();
		I_DAO_SanPham i_DAO_SanPham = new DAO_SanPham();
		I_DAO_TaiKhoan i_DAO_TaiKhoan = new DAO_TaiKhoan();
		I_DAO_ThoLamDan i_DAO_ThoLamDan = new DAO_ThoLamDan();
		I_DAO_ThongKe i_DAO_ThongKe = new DAO_ThongKe();

		LocateRegistry.createRegistry(5511);
		context.bind(URL + "I_DAO_BangPhanCong", i_DAO_BangPhanCong);
		context.bind(URL + "I_DAO_ChamCongNhanVien", i_DAO_ChamCongNhanVien);
		context.bind(URL + "I_DAO_ChamCongThoLamDan", i_DAO_ChamCongThoLamDan);
		context.bind(URL + "I_DAO_CongDoan", i_DAO_CongDoan);
		context.bind(URL + "I_DAO_CongNhanVien", i_DAO_CongNhanVien);
		context.bind(URL + "I_DAO_Dan", i_DAO_Dan);
		context.bind(URL + "I_DAO_LuongNhanVien", i_DAO_LuongNhanVien);
		context.bind(URL + "I_DAO_LuongThoLamDan", i_DAO_LuongThoLamDan);
		context.bind(URL + "I_DAO_NhanVien", i_DAO_NhanVien);
		context.bind(URL + "I_DAO_PhongBan", i_DAO_PhongBan);
		context.bind(URL + "I_DAO_SanPham", i_DAO_SanPham);
		context.bind(URL + "I_DAO_TaiKhoan", i_DAO_TaiKhoan);
		context.bind(URL + "I_DAO_ThoLamDan", i_DAO_ThoLamDan);
		context.bind(URL + "I_DAO_ThongKe", i_DAO_ThongKe);

		System.out.println("Server is running...");

	}
}
