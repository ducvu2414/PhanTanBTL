package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import dao.DAO_CongNhanVien;
import dao.DAO_ThoLamDan;
import entity.GioiTinh;
import entity.TayNghe;
import entity.ThoLamDan;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class FRM_QuanLyThoLamDan extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelThoLamDan;
	private JTextField jmaTLD;
	private JTextField jcmnd;
	private JTextField jhoTen;
	private JTextField jsdt;
	private JTextField jdiaChi;
	private JRadioButton rdbtnNewRadioButton_5, rdbtnNewRadioButton_4;
	private JDateChooser jngayVaoLam, jngaySinh;
	private ButtonGroup buttonGroup;
	private JButton btnThem, btnXoaRong, btnSua;
	private JComboBox<String> jtayNghe, jtrangThai;
	private DAO_CongNhanVien dao_cnv;
	private DAO_ThoLamDan dao_tld;
	private List<ThoLamDan> listtld;
	private JTable table_1;

	public FRM_QuanLyThoLamDan() {

		try {
			dao_cnv = new DAO_CongNhanVien();
			dao_tld = new DAO_ThoLamDan();
			setLayout(null);
			setBackground(new Color(221, 242, 251));

			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(325, 5, 800, 70);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
			add(lblNewLabel);
			lblNewLabel.setText("THỢ LÀM ĐÀN");

			JSeparator separator_1 = new JSeparator();
			separator_1.setBackground(Color.BLACK);
			separator_1.setForeground(Color.BLACK);
			separator_1.setBounds(15, 430, 1420, 3);
			add(separator_1);

			JPanel panel_2 = new JPanel();
			panel_2.setBackground(Color.WHITE);
			panel_2.setBounds(15, 490, 1420, 200);
			add(panel_2);
			panel_2.setLayout(null);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(20, 10, 1380, 180);
			panel_2.add(scrollPane_1);

			table_1 = new JTable();
			scrollPane_1.setViewportView(table_1);
			JTableHeader tb1 = table_1.getTableHeader();
			tb1.setBackground(new Color(221, 242, 251));
			tb1.setFont(new Font("Tahoma", Font.BOLD, 16));
			table_1.setRowHeight(30);
			table_1.setIntercellSpacing(new Dimension(0, 5));
			TableColumnModel columnModel = table_1.getColumnModel();
			columnModel.setColumnSelectionAllowed(false);
			columnModel.setColumnMargin(0);
			table_1.getTableHeader().setReorderingAllowed(false);

			String[] colHeader = { "Mã Thợ Làm Đàn", "Họ Tên Thợ Làm Đàn", "Giới Tính", "Ngày Sinh", "CMND", "SDT" };
			modelThoLamDan = new DefaultTableModel(colHeader, 0) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = { false, false, false, false, false, false };

				@Override
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			table_1.setModel(modelThoLamDan);

			JLabel lblNewLabel_6 = new JLabel("New label");
			lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNewLabel_6.setBounds(50, 450, 283, 25);
			add(lblNewLabel_6);
			lblNewLabel_6.setText("Danh sách thợ làm đàn");

			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(15, 90, 1420, 315);
			add(panel);
			panel.setLayout(null);

			JLabel lblNewLabel_6_1 = new JLabel("Thông tin thợ làm đàn");
			lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNewLabel_6_1.setBounds(35, 15, 304, 25);
			panel.add(lblNewLabel_6_1);

			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(91, 50, 560, 25);
			panel.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel_1 = new JLabel("Mã thợ làm đàn:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1.setBounds(31, 0, 140, 25);
			panel_1.add(lblNewLabel_1);

			jmaTLD = new JTextField();
			jmaTLD.setEditable(false);
			jmaTLD.setBounds(180, 0, 380, 25);
			panel_1.add(jmaTLD);
			jmaTLD.setColumns(10);

			JPanel panel_1_1 = new JPanel();
			panel_1_1.setLayout(null);
			panel_1_1.setBackground(Color.WHITE);
			panel_1_1.setBounds(91, 130, 560, 25);
			panel.add(panel_1_1);

			JLabel lblNewLabel_1_1 = new JLabel("CMND:");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_1.setBounds(31, 0, 140, 25);
			panel_1_1.add(lblNewLabel_1_1);

			jcmnd = new JTextField();
			jcmnd.setColumns(10);
			jcmnd.setBounds(180, 0, 380, 25);
			panel_1_1.add(jcmnd);

			JPanel panel_1_2 = new JPanel();
			panel_1_2.setLayout(null);
			panel_1_2.setBackground(Color.WHITE);
			panel_1_2.setBounds(759, 50, 560, 25);
			panel.add(panel_1_2);

			JLabel lblNewLabel_1_2 = new JLabel("Họ tên:");
			lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_2.setBounds(31, 0, 140, 25);
			panel_1_2.add(lblNewLabel_1_2);

			jhoTen = new JTextField();
			jhoTen.setColumns(10);
			jhoTen.setBounds(180, 0, 380, 25);
			panel_1_2.add(jhoTen);

			JPanel panel_1_3 = new JPanel();
			panel_1_3.setLayout(null);
			panel_1_3.setBackground(Color.WHITE);
			panel_1_3.setBounds(759, 130, 560, 25);
			panel.add(panel_1_3);

			JLabel lblNewLabel_1_3 = new JLabel("Số điện thoại:");
			lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_3.setBounds(31, 0, 140, 25);
			panel_1_3.add(lblNewLabel_1_3);

			jsdt = new JTextField();
			jsdt.setColumns(10);
			jsdt.setBounds(180, 0, 380, 25);
			panel_1_3.add(jsdt);

			JPanel panel_1_4 = new JPanel();
			panel_1_4.setBackground(Color.WHITE);
			panel_1_4.setBounds(91, 90, 560, 25);
			panel.add(panel_1_4);
			panel_1_4.setLayout(null);

			JLabel lblNewLabel_4 = new JLabel("Giới tính:");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_4.setBounds(32, 0, 93, 25);
			panel_1_4.add(lblNewLabel_4);

			rdbtnNewRadioButton_5 = new JRadioButton("");
			rdbtnNewRadioButton_5.setSelected(true);
			rdbtnNewRadioButton_5.setBackground(new Color(255, 255, 255));
			rdbtnNewRadioButton_5.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnNewRadioButton_5.setBounds(187, 0, 25, 25);
			panel_1_4.add(rdbtnNewRadioButton_5);

			JLabel lblNewLabel_3 = new JLabel("Nam");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_3.setBounds(218, 0, 93, 25);
			panel_1_4.add(lblNewLabel_3);

			rdbtnNewRadioButton_4 = new JRadioButton("");
			rdbtnNewRadioButton_4.setBackground(new Color(255, 255, 255));
			rdbtnNewRadioButton_4.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnNewRadioButton_4.setBounds(373, 0, 25, 25);
			panel_1_4.add(rdbtnNewRadioButton_4);

			JLabel lblNewLabel_2 = new JLabel("Nữ");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_2.setBounds(404, 0, 93, 25);
			panel_1_4.add(lblNewLabel_2);

			buttonGroup = new ButtonGroup();
			buttonGroup.add(rdbtnNewRadioButton_4);
			buttonGroup.add(rdbtnNewRadioButton_5);

			JPanel panel_1_5 = new JPanel();
			panel_1_5.setLayout(null);
			panel_1_5.setBackground(Color.WHITE);
			panel_1_5.setBounds(91, 170, 560, 25);
			panel.add(panel_1_5);

			JLabel lblNewLabel_1_4 = new JLabel("Tay nghề:");
			lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_4.setBounds(31, 0, 140, 25);
			panel_1_5.add(lblNewLabel_1_4);

			jtayNghe = new JComboBox<>();
			jtayNghe.addItem(TayNghe.Bac1.getValue());
			jtayNghe.addItem(TayNghe.Bac2.getValue());
			jtayNghe.addItem(TayNghe.Bac3.getValue());
			jtayNghe.addItem(TayNghe.Bac4.getValue());
			jtayNghe.addItem(TayNghe.Bac5.getValue());
			jtayNghe.setBounds(180, 0, 380, 25);
			panel_1_5.add(jtayNghe);

			JPanel panel_1_6 = new JPanel();
			panel_1_6.setLayout(null);
			panel_1_6.setBackground(Color.WHITE);
			panel_1_6.setBounds(91, 210, 560, 25);
			panel.add(panel_1_6);

			JLabel lblNewLabel_1_5 = new JLabel("Ngày vào làm:");
			lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_5.setBounds(31, 0, 140, 25);
			panel_1_6.add(lblNewLabel_1_5);

			jngayVaoLam = new JDateChooser();
			jngayVaoLam.setLocation(180, 0);
			jngayVaoLam.setSize(380, 25);
			jngayVaoLam.setDateFormatString("dd-MM-yyyy");
			java.util.Date currentDate = new java.util.Date();
			jngayVaoLam.setDate(currentDate);
			panel_1_6.add(jngayVaoLam);

			JPanel panel_1_7 = new JPanel();
			panel_1_7.setLayout(null);
			panel_1_7.setBackground(Color.WHITE);
			panel_1_7.setBounds(759, 170, 560, 25);
			panel.add(panel_1_7);

			JLabel lblNewLabel_1_6 = new JLabel("Địa chỉ:");
			lblNewLabel_1_6.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_6.setBounds(31, 0, 140, 25);
			panel_1_7.add(lblNewLabel_1_6);

			jdiaChi = new JTextField();
			jdiaChi.setColumns(10);
			jdiaChi.setBounds(180, 0, 380, 25);
			panel_1_7.add(jdiaChi);

			JPanel panel_1_8 = new JPanel();
			panel_1_8.setLayout(null);
			panel_1_8.setBackground(Color.WHITE);
			panel_1_8.setBounds(759, 90, 560, 25);
			panel.add(panel_1_8);

			JLabel lblNewLabel_1_7 = new JLabel("Ngày sinh:");
			lblNewLabel_1_7.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_7.setBounds(31, 0, 140, 25);
			panel_1_8.add(lblNewLabel_1_7);

			jngaySinh = new JDateChooser();
			jngaySinh.setDateFormatString("dd-MM-yyyy");
			jngaySinh.setBounds(180, 0, 380, 25);
			jngaySinh.setDate(currentDate);
			panel_1_8.add(jngaySinh);

			JPanel panel_1_9 = new JPanel();
			panel_1_9.setLayout(null);
			panel_1_9.setBackground(Color.WHITE);
			panel_1_9.setBounds(759, 210, 560, 25);
			panel.add(panel_1_9);

			JLabel lblNewLabel_1_8 = new JLabel("Trạng thái:");
			lblNewLabel_1_8.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1_8.setBounds(31, 0, 140, 25);
			panel_1_9.add(lblNewLabel_1_8);

			jtrangThai = new JComboBox<>();
			jtrangThai.addItem("Đang làm");
			jtrangThai.addItem("Nghỉ làm");
			jtrangThai.setBounds(180, 0, 380, 25);
			panel_1_9.add(jtrangThai);

			btnXoaRong = new JButton("Xoá rỗng");
			btnXoaRong.setForeground(Color.WHITE);
			btnXoaRong.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnXoaRong.setBackground(new Color(2, 104, 156));
			btnXoaRong.setBounds(900, 260, 170, 40);
			panel.add(btnXoaRong);

			btnSua = new JButton("Sửa thông tin");

			btnSua.setForeground(Color.WHITE);
			btnSua.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnSua.setBackground(new Color(2, 104, 156));
			btnSua.setBounds(625, 260, 170, 40);
			panel.add(btnSua);

			btnThem = new JButton("Thêm");
			btnThem.setForeground(Color.WHITE);
			btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnThem.setBackground(new Color(2, 104, 156));
			btnThem.setBounds(350, 260, 170, 40);
			panel.add(btnThem);

			btnThem.addActionListener(this);
			btnXoaRong.addActionListener(this);
			btnSua.addActionListener(this);

			table_1.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int row = table_1.getSelectedRow();
					if (row >= 0 && row < listtld.size()) {
						jhoTen.setEnabled(false);
						ThoLamDan tld = listtld.get(row);
						jmaTLD.setText(tld.getMaCongNhanVien());
						jhoTen.setText(tld.getHoTen());
						jcmnd.setText(tld.getMaCanCuocCongDan());
						jdiaChi.setText(tld.getDiaChi());
						jsdt.setText(tld.getSoDienThoai());

						jtayNghe.setSelectedItem(tld.getTayNghe().getValue());
						jtrangThai.setSelectedItem(tld.isTrangThai() ? "Đang Làm" : "Nghỉ Việc");
						String gioiTinhValue = modelThoLamDan.getValueAt(row, 2).toString();
						if ("Nữ".equals(gioiTinhValue)) {
							rdbtnNewRadioButton_4.setSelected(true);
						} else {
							rdbtnNewRadioButton_5.setSelected(true);
						}
						java.sql.Date getNgaySinh = tld.getNgaySinh();
						jngaySinh.setDate(getNgaySinh);
						java.sql.Date getNgayVaoLam = tld.getNgaySinh();
						jngayVaoLam.setDate(getNgayVaoLam);
					}
				}
			});

			updateTableDataThoLamDan();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateTableDataThoLamDan() throws RemoteException {
		listtld = dao_tld.docTuBang();
		modelThoLamDan.setRowCount(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (ThoLamDan thoLamDan : listtld) {
			String gioiTinh = thoLamDan.getGioiTinh().getValue();
			String[] rowData = { thoLamDan.getMaCongNhanVien(), thoLamDan.getHoTen(), gioiTinh,
					dateFormat.format(thoLamDan.getNgaySinh()), thoLamDan.getMaCanCuocCongDan(),
					thoLamDan.getSoDienThoai() };
			modelThoLamDan.addRow(rowData);
		}

	}

	public void xoaRong() {
		java.util.Date today = new java.util.Date();
		jmaTLD.setText("");
		jhoTen.setEnabled(true);
		jhoTen.setText("");
		jsdt.setText("");
		jcmnd.setText("");
		jdiaChi.setText("");
		jtayNghe.setSelectedIndex(0);
		jtrangThai.setSelectedIndex(0);
		jngaySinh.setDate(null);
		jngayVaoLam.setDate(today);
		rdbtnNewRadioButton_5.setSelected(true);
	}

	public boolean checkregex() {
		String cmnd = jcmnd.getText().trim();
		String hoTen = jhoTen.getText().trim();
		String sdt = jsdt.getText().trim();
		String diaChi = jdiaChi.getText().trim();
		java.util.Date today = new java.util.Date();
		Date ngaySinh = new Date(jngaySinh.getDate().getTime());
		Date ngayVaoLam = new Date(jngayVaoLam.getDate().getTime());
		if (!(cmnd.length() > 0 && cmnd.matches("^[0-9]{12}$"))) {
			JOptionPane.showMessageDialog(this, "Error : CMND phải có 12 số");
			return false;
		}
		if (!(sdt.length() > 0 && sdt.matches("^0[0-9]{9}$"))) {
			JOptionPane.showMessageDialog(this, "Error : Số điện thoại bắt đầu từ số 0");
			return false;
		}
		if (!(hoTen.length() > 0 && hoTen.matches("^[\\p{L}\\s]+$"))) {
			JOptionPane.showMessageDialog(this, "Error : Họ tên phải là ký tự");
			return false;
		}
		if (!(diaChi.length() > 0 && diaChi.matches("^[\\p{L}\\d\\s.,]+$"))) {
			JOptionPane.showMessageDialog(this, "Error : Địa chỉ phải là ký tự");
			return false;
		}
		if (ngaySinh.after(today)
				|| (int) ((today.getTime() - ngaySinh.getTime()) / (1000 * 60 * 24 * 60 * 365)) < 18) {
			JOptionPane.showMessageDialog(null, "Ngày Sinh Không Hợp Lệ!!!");
			return false;
		}
		if (ngayVaoLam.after(today)) {
			JOptionPane.showMessageDialog(null, "Ngày Vào Làm Phải Trước Ngày Hiện Tại !!!");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnXoaRong)) {
			xoaRong();
		} else if (o.equals(btnThem)) {
			int row = table_1.getSelectedRow();
			if (row == -1) {
				if (checkregex()) {
					String hoTen = jhoTen.getText().trim();
					String sdt = jsdt.getText().trim();
					String cmnd = jcmnd.getText().trim();
					String diaChi = jdiaChi.getText().trim();
					String tn = (String) jtayNghe.getSelectedItem();
					TayNghe tayNghe;
					if (tn.equals("Bậc 1")) {
						tayNghe = TayNghe.Bac1;
					} else if (tn.equals("Bậc 2")) {
						tayNghe = TayNghe.Bac2;
					} else if (tn.equals("Bậc 3")) {
						tayNghe = TayNghe.Bac3;
					} else if (tn.equals("Bậc 4")) {
						tayNghe = TayNghe.Bac4;
					} else {
						tayNghe = TayNghe.Bac5;
					}

					java.sql.Date ngaySinh = new java.sql.Date(jngaySinh.getDate().getTime());
					java.sql.Date ngayVaoLam = new java.sql.Date(jngayVaoLam.getDate().getTime());

					boolean trangThai = true;
					if (jtrangThai.getSelectedItem().equals("Đang Làm")) {
						trangThai = true;
					} else if (jtrangThai.getSelectedItem().equals("Nghỉ Làm")) {
						trangThai = false;
					}

					GioiTinh phai = GioiTinh.Nam;
					if (buttonGroup.getSelection() != null) {
						if (buttonGroup.getSelection().equals(rdbtnNewRadioButton_5.getModel())) {
							phai = GioiTinh.Nam;
						} else if (buttonGroup.getSelection().equals(rdbtnNewRadioButton_4.getModel())) {
							phai = GioiTinh.Nu;
						}
					}

					try {
						if (dao_cnv.isDuplicate(hoTen, cmnd, sdt)) {
							JOptionPane.showMessageDialog(this,
									"Họ tên, Căn cước công dân hoặc số điện thoại đã tồn tại");
							return;
						} else {
							ThoLamDan tld = new ThoLamDan(hoTen, phai, ngaySinh, cmnd, sdt, diaChi, trangThai,
									ngayVaoLam, tayNghe);
							dao_tld.taoTLD(tld);
							modelThoLamDan.setRowCount(0);
							updateTableDataThoLamDan();
							modelThoLamDan.fireTableDataChanged();
							JOptionPane.showMessageDialog(this, "Thêm thành công");
							xoaRong();
						}
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} else {
				JOptionPane.showMessageDialog(this, "Thợ làm đàn đã có không thể thêm");
				xoaRong();
			}
		} else if (o.equals(btnSua)) {
			int row = table_1.getSelectedRow();
			if (row >= 0) {
				if (checkregex()) {
					if (row >= 0) {
						String maThoLamDan = jmaTLD.getText().trim();
						String hoTen = jhoTen.getText().trim();
						String sdt = jsdt.getText().trim();
						String cmnd = jcmnd.getText().trim();
						String diaChi = jdiaChi.getText().trim();
						String tn = (String) jtayNghe.getSelectedItem();
						TayNghe tayNghe;
						if (tn.equals("Bậc 1")) {
							tayNghe = TayNghe.Bac1;
						} else if (tn.equals("Bậc 2")) {
							tayNghe = TayNghe.Bac2;
						} else if (tn.equals("Bậc 3")) {
							tayNghe = TayNghe.Bac3;
						} else if (tn.equals("Bậc 4")) {
							tayNghe = TayNghe.Bac4;
						} else {
							tayNghe = TayNghe.Bac5;
						}

						java.sql.Date ngaySinh = new java.sql.Date(jngaySinh.getDate().getTime());
						java.sql.Date ngayVaoLam = new java.sql.Date(jngayVaoLam.getDate().getTime());
						boolean trangThai = true;
						if (jtrangThai.getSelectedItem().equals("Đang Làm")) {
							trangThai = true;
						} else if (jtrangThai.getSelectedItem().equals("Nghỉ Làm")) {
							trangThai = false;
						}
						GioiTinh phai = GioiTinh.Nam;
						if (buttonGroup.getSelection() != null) {
							if (buttonGroup.getSelection().equals(rdbtnNewRadioButton_5.getModel())) {
								phai = GioiTinh.Nam;
							} else if (buttonGroup.getSelection().equals(rdbtnNewRadioButton_4.getModel())) {
								phai = GioiTinh.Nu;
							}
						}

						String maThoLamDan1 = modelThoLamDan.getValueAt(row, 0).toString();
						ThoLamDan tld;
						try {
							tld = dao_tld.getTLDTheoMaThoLamDan(maThoLamDan1);
							tld.setHoTen(hoTen);
							tld.setGioiTinh(phai);
							tld.setNgaySinh(ngaySinh);
							tld.setMaCanCuocCongDan(cmnd);
							tld.setSoDienThoai(sdt);
							tld.setDiaChi(diaChi);
							tld.setTrangThai(trangThai);
							tld.setNgayVaoLam(ngayVaoLam);
							tld.setTayNghe(tayNghe);

							if (dao_tld.update(tld)) {
								table_1.setValueAt(maThoLamDan, row, 0);
								table_1.setValueAt(hoTen, row, 1);
								table_1.setValueAt(phai.getValue(), row, 2);
								SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
								table_1.setValueAt(dateFormat.format(ngaySinh), row, 3);
								table_1.setValueAt(cmnd, row, 4);
								table_1.setValueAt(sdt, row, 5);
								JOptionPane.showMessageDialog(this, "Sửa thành công");
								updateTableDataThoLamDan();
							}

							xoaRong();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				} else {
					JOptionPane.showMessageDialog(this, "Không có thợ làm đàn để sửa");

				}
			}
		}
	}
}