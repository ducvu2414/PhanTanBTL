package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import entity.BangChamCongNhanVien;
import entity.BangLuongNhanVien;
import entity.NhanVien;
import entity.PhongBan;
import idao.I_DAO_ChamCongNhanVien;
import idao.I_DAO_LuongNhanVien;
import idao.I_DAO_NhanVien;
import idao.I_DAO_PhongBan;

import javax.swing.JScrollPane;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class FRM_ChamCongNhanVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private static String URL = "rmi://DESKTOP-UH6CFJP:5511/";

	private JTable tableChamCong;
	private DefaultTableModel modelChamCong;
	private JButton btnChamCong;
	private JDateChooser ngayPhanCong;
	private I_DAO_NhanVien dao_NhanVien;
	private I_DAO_ChamCongNhanVien dao_ChamCongNhanVien;
	private I_DAO_LuongNhanVien dao_LuongNhanVien;
	private I_DAO_PhongBan dao_PhongBan;
	private JTextField txtTen;

	/**
	 * Create the panel.
	 */
	public FRM_ChamCongNhanVien() {
		try {
			dao_NhanVien = (I_DAO_NhanVien) Naming.lookup(URL + "I_DAO_NhanVien");
			dao_ChamCongNhanVien = (I_DAO_ChamCongNhanVien) Naming.lookup(URL + "I_DAO_ChamCongNhanVien");
			dao_LuongNhanVien = (I_DAO_LuongNhanVien) Naming.lookup(URL + "I_DAO_LuongNhanVien");
			dao_PhongBan = (I_DAO_PhongBan) Naming.lookup(URL + "I_DAO_PhongBan");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}

		setBackground(new Color(221, 242, 251));
		setLayout(null);
		ngayPhanCong = new JDateChooser();
		ngayPhanCong.setSize(new Dimension(30, 20));
		ngayPhanCong.setDateFormatString("dd-MM-yyyy");
		try {
			Date date = Date.valueOf(LocalDate.now());
			ngayPhanCong.setDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ngayPhanCong.setBounds(200, 85, 130, 30);
		ngayPhanCong.setEnabled(false);
		add(ngayPhanCong);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 130, 1430, 470);
		add(scrollPane);

		tableChamCong = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= 2;
			}
		};
		tableChamCong.setBackground(new Color(255, 255, 255));
		tableChamCong.setEditingRow(0);
		tableChamCong.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(tableChamCong);

		scrollPane.setViewportView(tableChamCong);
		tableChamCong.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, },
				new String[] { "M\u00E3 Nh\u00E2n Vi\u00EAn", "T\u00EAn Nh\u00E2n Vi\u00EAn", "Tr\u1EA1ng Th\u00E1i",
						"S\u1ED1 gi\u1EDD t\u0103ng ca" }));
		TableColumnModel columnModel = tableChamCong.getColumnModel();
		columnModel.setColumnSelectionAllowed(false);
		columnModel.setColumnMargin(0);
		tableChamCong.getTableHeader().setReorderingAllowed(false);
		String[] gioTangCaLuaChon = { "0", "1", "2", "3", "4" };
		JComboBox<String> gioTangCaComboBox = new JComboBox<>(gioTangCaLuaChon);
		DefaultCellEditor editorGioTangCa = new DefaultCellEditor(gioTangCaComboBox);
		TableColumn gioTangCaColumn = tableChamCong.getColumnModel().getColumn(3);
		gioTangCaColumn.setCellEditor(editorGioTangCa);
		gioTangCaComboBox.setSelectedItem("0");

		String[] trangThaiLuaChon = { "Chưa ghi nhận công", "Làm nguyên ngày công", "Làm nửa ngày công", "Nghỉ phép",
				"Nghỉ không phép" };
		JComboBox<String> trangThaiComboBox = new JComboBox<>(trangThaiLuaChon);
		DefaultCellEditor editorTrangThai = new DefaultCellEditor(trangThaiComboBox);
		TableColumn trangThaiColumn = tableChamCong.getColumnModel().getColumn(2);
		trangThaiColumn.setCellEditor(editorTrangThai);
		trangThaiComboBox.setSelectedItem("Chưa ghi nhận công");

		modelChamCong = (DefaultTableModel) tableChamCong.getModel();

		JTableHeader tb = tableChamCong.getTableHeader();
		tb.setBackground(new Color(151, 201, 219));
		tb.setFont(new Font("Tahoma", Font.BOLD, 16));
		int rowHeight = 30;
		int rowMargin = 10;
		tableChamCong.setRowHeight(rowHeight);
		tableChamCong.setIntercellSpacing(new java.awt.Dimension(0, rowMargin));
		JComboBox<String> comboBoxPhongBan = new JComboBox<String>();
		comboBoxPhongBan.setBounds(1199, 85, 100, 30);
		add(comboBoxPhongBan);
		comboBoxPhongBan.addItem("Tất Cả");

		List<PhongBan> listPhongBan;
		try {
			listPhongBan = dao_PhongBan.getTatCaPhongBan();
			for (PhongBan phongBan : listPhongBan) {
				comboBoxPhongBan.addItem(phongBan.getTenPhongBan());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comboBoxPhongBan.setSelectedIndex(0);
		btnChamCong = new JButton("Chấm Công");
		btnChamCong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int output = JOptionPane.showConfirmDialog(null, "Bạn xác nhận chấm công", "Thông báo xác nhận",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (output == JOptionPane.YES_OPTION) {
					int check = 0;
					java.util.Date utilDate = new java.util.Date();
					Date date = new Date(utilDate.getTime());
					List<BangChamCongNhanVien> listBCC;
					try {
						listBCC = dao_ChamCongNhanVien.layDanhSachChamCong(date);
						List<String> listNV = new ArrayList<>();
						for (BangChamCongNhanVien bangChamCongNhanVien : listBCC) {
							listNV.add(bangChamCongNhanVien.getNhanVien().getMaCongNhanVien());
						}

						for (int row = 0; row < modelChamCong.getRowCount(); row++) {
							if (listNV.contains((String) modelChamCong.getValueAt(row, 0))) {

							} else {
								String trangThaiDiLam = (String) modelChamCong.getValueAt(row, 2);
								if (!trangThaiDiLam.equals("Chưa ghi nhận công")) {
									check++;
									String maNhanVien = (String) modelChamCong.getValueAt(row, 0);
									NhanVien nhanVien = dao_NhanVien.getNhanVienTheoMa(maNhanVien);
									BangChamCongNhanVien bangChamCong = new BangChamCongNhanVien();
									long currentTimeMillis = System.currentTimeMillis();
									Date currentDate = new Date(currentTimeMillis);
									bangChamCong.setNhanVien(nhanVien);

									bangChamCong.setTrangThaiDiLam((String) modelChamCong.getValueAt(row, 2));
									String trangThai = (String) modelChamCong.getValueAt(row, 2);
									int soGioTangCa;
									if (trangThai.equals("Nghỉ phép") || trangThai.equals("Nghỉ không phép")) {
										soGioTangCa = 0;
									} else {
										soGioTangCa = Integer.parseInt((String) modelChamCong.getValueAt(row, 3));
									}

									bangChamCong.setSoGioTangCa(soGioTangCa);
									bangChamCong.setNgayChamCong(currentDate);

									LocalDate currentDate1 = LocalDate.now();
									Month currentMonth = currentDate1.getMonth();
									int thang = currentMonth.getValue();
									int nam = currentDate1.getYear();
									boolean KT = dao_LuongNhanVien.kiemTraTrungMa(thang, nam,
											nhanVien.getMaCongNhanVien());
									System.out.println(KT);
									if (!KT) {
										themBangLuong(nhanVien);
									}
									String maBangLuong = dao_LuongNhanVien.getMaBangLuong(thang, nam,
											nhanVien.getMaCongNhanVien());
									BangLuongNhanVien bangLuong = dao_LuongNhanVien.getBangLuongTheoMa(maBangLuong);
									bangChamCong.setBangLuong(bangLuong);
									try {
										boolean kiemTra = dao_ChamCongNhanVien.KiemTraTrung(bangChamCong);
										if (!kiemTra) {
											dao_ChamCongNhanVien.themBangChamCong(bangChamCong);
										}
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}

							}

						}
						if (check != 0) {
							JOptionPane.showMessageDialog(null, "Chấm Công Thành Công");
						} else {
							JOptionPane.showMessageDialog(null, "Không có nhân viên nào để chấm công");

						}
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Chấm Công Thất Bại");
						e2.printStackTrace();
					}

				}
				String selectedValue = (String) comboBoxPhongBan.getSelectedItem();
				if (selectedValue.equals("Tất Cả")) {
					loadDataIntoTableChamCong();
					modelChamCong.fireTableDataChanged();
				} else {
					PhongBan pb;
					try {
						pb = dao_PhongBan.getPhongBanTheoTen(selectedValue);
						loadDataIntoTableChamCongTheoPhongBan(pb.getMaPhongBan());
						modelChamCong.fireTableDataChanged();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		});
		btnChamCong.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnChamCong.setForeground(Color.WHITE);
		btnChamCong.setBackground(new Color(2, 104, 156));
		btnChamCong.setBounds(525, 630, 170, 50);
		add(btnChamCong);
		txtTen = new JTextField();
		txtTen.setColumns(10);
		txtTen.setBackground(Color.WHITE);
		txtTen.setBounds(563, 85, 210, 30);
		add(txtTen);

		txtTen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ten = txtTen.getText().trim();
				if (ten.equals("")) {
					loadDataIntoTableChamCong();
					modelChamCong.fireTableDataChanged();
				} else {
					loadDataIntoTableChamCongTheoTen(ten);
					modelChamCong.fireTableDataChanged();
				}
			}
		});
		JButton btnTimKiemTen = new JButton("");
		btnTimKiemTen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ten = txtTen.getText().trim();
				if (ten.equals("")) {
					loadDataIntoTableChamCong();
					modelChamCong.fireTableDataChanged();
				} else {
					loadDataIntoTableChamCongTheoTen(ten);
					modelChamCong.fireTableDataChanged();
				}
			}
		});
		btnTimKiemTen.setIcon(new ImageIcon(FRM_ChamCongNhanVien.class.getResource("/icons/search_icon.png")));
		btnTimKiemTen.setForeground(Color.WHITE);
		btnTimKiemTen.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTimKiemTen.setBackground(new Color(2, 104, 156));
		btnTimKiemTen.setBounds(783, 85, 61, 30);
		add(btnTimKiemTen);
		JButton btnMacDinh = new JButton("Mặc định");
		btnMacDinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int row = 0; row < modelChamCong.getRowCount(); row++) {
					if (modelChamCong.getValueAt(row, 2).equals("Chưa ghi nhận công")) {
						modelChamCong.setValueAt("Làm nguyên ngày công", row, 2);
						modelChamCong.setValueAt("0", row, 3);
					}
				}
			}
		});
		btnMacDinh.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnMacDinh.setForeground(Color.WHITE);
		btnMacDinh.setBackground(new Color(120, 186, 219));
		btnMacDinh.setBounds(755, 630, 170, 50);
		add(btnMacDinh);

		JLabel lblNgayChamCong = new JLabel("Ngày Chấm Công");
		lblNgayChamCong.setBounds(10, 85, 140, 30);
		lblNgayChamCong.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblNgayChamCong);
		JLabel lblNewLabel = new JLabel("BẢNG CHẤM CÔNG NHÂN VIÊN");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setForeground((new Color(0, 27, 72)));

		lblNewLabel.setBounds(300, 5, 850, 90);
		add(lblNewLabel);

		JLabel lblTmKimTheo = new JLabel("Tìm kiếm theo tên");
		lblTmKimTheo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTmKimTheo.setBounds(375, 85, 160, 30);
		add(lblTmKimTheo);

		JLabel lblTmKimTheo_2 = new JLabel("Tìm kiếm theo phòng ban");
		lblTmKimTheo_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTmKimTheo_2.setBounds(927, 85, 223, 30);
		add(lblTmKimTheo_2);

		comboBoxPhongBan.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedValue = (String) comboBoxPhongBan.getSelectedItem();
					if (selectedValue.equals("Tất Cả")) {
						loadDataIntoTableChamCong();
						modelChamCong.fireTableDataChanged();
					} else {

						PhongBan pb;
						try {
							pb = dao_PhongBan.getPhongBanTheoTen(selectedValue);
							loadDataIntoTableChamCongTheoPhongBan(pb.getMaPhongBan());
							modelChamCong.fireTableDataChanged();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});
		loadDataIntoTableChamCong();
	}

	private void loadDataIntoTableChamCong() {
		modelChamCong.setRowCount(0);
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		try {
			int sl = dao_ChamCongNhanVien.kiemTraSoLuongDaChamCong(date);
			if (sl == 0) {
				for (NhanVien nhanVien : dao_NhanVien.getAllListNhanVien()) {
					Object[] objects = { nhanVien.getMaCongNhanVien(), nhanVien.getHoTen(), "Chưa ghi nhận công", "0" };
					modelChamCong.addRow(objects);
				}
			} else {

				List<BangChamCongNhanVien> listBCC = dao_ChamCongNhanVien.layDanhSachChamCong(date);
				List<String> listNV = new ArrayList<>();
				for (BangChamCongNhanVien bangChamCongNhanVien : listBCC) {
					listNV.add(bangChamCongNhanVien.getNhanVien().getMaCongNhanVien());
				}

				for (NhanVien nhanVien : dao_NhanVien.getAllListNhanVien()) {
					if (listNV.contains(nhanVien.getMaCongNhanVien())) {
						int position = listNV.indexOf(nhanVien.getMaCongNhanVien());
						Object[] objects = { listBCC.get(position).getNhanVien().getMaCongNhanVien(),
								listBCC.get(position).getNhanVien().getHoTen(),
								listBCC.get(position).getTrangThaiDiLam(), listBCC.get(position).getSoGioTangCa() };
						modelChamCong.addRow(objects);
					} else {
						Object[] objects = { nhanVien.getMaCongNhanVien(), nhanVien.getHoTen(), "Chưa ghi nhận công",
								"0" };
						modelChamCong.addRow(objects);
					}

				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}

	}

	private void loadDataIntoTableChamCongTheoTen(String ten) {
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		try {
			int sl = dao_ChamCongNhanVien.kiemTraTimKiemTheoTen(ten);
			if (sl == 0) {
				modelChamCong.setRowCount(0);
				JOptionPane.showMessageDialog(null, "Không có tên nhân viên tìm kiếm");
			} else {
				modelChamCong.setRowCount(0);
				List<BangChamCongNhanVien> listBCC = dao_ChamCongNhanVien.layDanhSachChamCongTheoTen(date, ten);
				List<String> listNV = new ArrayList<>();
				for (BangChamCongNhanVien bangChamCongNhanVien : listBCC) {
					listNV.add(bangChamCongNhanVien.getNhanVien().getMaCongNhanVien());
				}

				for (NhanVien nhanVien : dao_NhanVien.getAllNhanVienTheoTen(ten)) {
					if (listNV.contains(nhanVien.getMaCongNhanVien())) {
						int position = listNV.indexOf(nhanVien.getMaCongNhanVien());
						Object[] objects = { listBCC.get(position).getNhanVien().getMaCongNhanVien(),
								listBCC.get(position).getNhanVien().getHoTen(),
								listBCC.get(position).getTrangThaiDiLam(), listBCC.get(position).getSoGioTangCa() };
						modelChamCong.addRow(objects);
					} else {
						Object[] objects = { nhanVien.getMaCongNhanVien(), nhanVien.getHoTen(), "Chưa ghi nhận công",
								"0" };
						modelChamCong.addRow(objects);
					}

				}
			}
			modelChamCong.fireTableDataChanged();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}

	}

	private void loadDataIntoTableChamCongTheoPhongBan(String maPhongBan) {
		modelChamCong.setRowCount(0);
		java.util.Date utilDate = new java.util.Date();
		Date date = new Date(utilDate.getTime());
		try {

			List<BangChamCongNhanVien> listBCC = dao_ChamCongNhanVien.layDanhSachChamCongTheoPhongBan(date, maPhongBan);
			List<String> listNV = new ArrayList<>();
			for (BangChamCongNhanVien bangChamCongNhanVien : listBCC) {
				listNV.add(bangChamCongNhanVien.getNhanVien().getMaCongNhanVien());
			}

			for (NhanVien nhanVien : dao_NhanVien.getAllNhanVienTheoPhongBan(maPhongBan)) {
				if (listNV.contains(nhanVien.getMaCongNhanVien())) {
//					int position = listNV.indexOf(nhanVien.getMaNhanVien());
//					Object[] objects = { listBCC.get(position).getNhanVien().getMaNhanVien(),
//							listBCC.get(position).getNhanVien().getCongNhanVien().getHoTen(),
//							listBCC.get(position).getTrangThaiDiLam(), listBCC.get(position).getSoGioTangCa() };
//					modelChamCong.addRow(objects);
				} else {
					Object[] objects = { nhanVien.getMaCongNhanVien(), nhanVien.getHoTen(), "Chưa ghi nhận công", "0" };
					modelChamCong.addRow(objects);
				}

			}

			modelChamCong.fireTableDataChanged();
		} catch (

		Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}

	private void themBangLuong(NhanVien nhanVien) {
		LocalDate currentDate = LocalDate.now();
		Month currentMonth = currentDate.getMonth();
		int thang = currentMonth.getValue();
		int nam = currentDate.getYear();
		BangLuongNhanVien bl = new BangLuongNhanVien();
		bl.setNam(nam);
		bl.setThang(thang);
		bl.setNhanVien(nhanVien);
		bl.setSoGioTangCaChuNhat(0);
		bl.setSoGioTangCaNgayThuong(0);
		bl.setSoNgayLamChuNhat(0);
		bl.setSoNgayThuongDiLam(0);
		bl.setSoNgayNghiCoPhep(0);
		bl.setSoNgayNghiKhongPhep(0);
		try {
			dao_LuongNhanVien.themBangLuongNhanVien(bl);
			String maBangLuong = dao_LuongNhanVien.getMaBangLuong(thang, nam, nhanVien.getMaCongNhanVien());
			bl.setMaBangLuong(maBangLuong);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}