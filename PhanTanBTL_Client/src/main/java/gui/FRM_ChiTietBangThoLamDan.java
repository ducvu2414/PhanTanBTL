package gui;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import entity.BangLuongThoLamDan;
import entity.CongDoan;
import entity.Dan;
import idao.I_DAO_ChamCongThoLamDan;
import idao.I_DAO_CongDoan;
import idao.I_DAO_Dan;
import idao.I_DAO_LuongThoLamDan;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.JScrollPane;

public class FRM_ChiTietBangThoLamDan extends JFrame {
	private static String URL = "rmi://DESKTOP-UH6CFJP:5511/";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblMaThoLamDan;
	private JLabel lblTenThoLamDan;
	private JTable table;
	private static I_DAO_ChamCongThoLamDan dao_ChamCongThoLamDan;
	private static I_DAO_LuongThoLamDan dao_LuongThoLamDan;
	private static I_DAO_Dan dao_Dan;
	private static I_DAO_CongDoan dao_CongDoan;
	private DefaultTableModel model_ChiTietBangLuong;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UIManager.setLookAndFeel(new FlatMacLightLaf());
//				} catch (Exception ex) {
//					System.err.println("Failed to initialize LaF");
//				}
//				try {
//					ChiTietBangLuong frame = new ChiTietBangLuong();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public FRM_ChiTietBangThoLamDan(BangLuongThoLamDan bangLuongThoLamDan) {

		try {
			dao_ChamCongThoLamDan = (I_DAO_ChamCongThoLamDan) Naming.lookup(URL + "I_DAO_ChamCongThoLamDan");
			dao_LuongThoLamDan = (I_DAO_LuongThoLamDan) Naming.lookup(URL + "I_DAO_LuongThoLamDan");
			dao_Dan = (I_DAO_Dan) Naming.lookup(URL + "I_DAO_Dan");
			dao_CongDoan = (I_DAO_CongDoan) Naming.lookup(URL + "I_DAO_CongDoan");

			setType(Type.POPUP);
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 1000, 744);
			setTitle("Phiếu Lương");
			contentPane = new JPanel();
			setLocationRelativeTo(null);
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JLabel lblMTLD = new JLabel("Mã Thợ Làm Đàn:");
			lblMTLD.setForeground(new Color(0, 0, 0));
			lblMTLD.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblMTLD.setBounds(25, 69, 300, 25);
			contentPane.add(lblMTLD);

			JLabel lblTenTLD = new JLabel("Tên Thợ Làm Đàn:");
			lblTenTLD.setForeground(new Color(0, 0, 0));
			lblTenTLD.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblTenTLD.setBounds(25, 114, 300, 25);
			contentPane.add(lblTenTLD);

			JLabel lblThongTin = new JLabel();
			String txt = "Phiếu Lương Tháng " + bangLuongThoLamDan.getThang() + " Năm " + bangLuongThoLamDan.getNam();
			lblThongTin.setText(txt);
			lblThongTin.setBounds(0, 10, 986, 49);
			lblThongTin.setHorizontalAlignment(SwingConstants.CENTER);
			lblThongTin.setFont(new Font("Tahoma", Font.BOLD, 30));
			lblThongTin.setForeground(new Color(255, 0, 0));
			contentPane.add(lblThongTin);

			lblMaThoLamDan = new JLabel();
			lblMaThoLamDan.setText(bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien());
			lblMaThoLamDan.setForeground(new Color(0, 0, 0));
			lblMaThoLamDan.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblMaThoLamDan.setBounds(242, 69, 300, 25);
			contentPane.add(lblMaThoLamDan);

			lblTenThoLamDan = new JLabel();
			lblTenThoLamDan.setText(bangLuongThoLamDan.getThoLamDan().getHoTen());
			lblTenThoLamDan.setForeground(new Color(0, 0, 0));
			lblTenThoLamDan.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblTenThoLamDan.setBounds(242, 114, 300, 25);
			contentPane.add(lblTenThoLamDan);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(25, 169, 934, 302);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);

			String[] colHeader = { "S\u1EA3n Ph\u1EA9m", "C\u00F4ng \u0110o\u1EA1n", "S\u1ED1 l\u01B0\u1EE3ng",
					"Gi\u00E1 C\u00F4ng \u0111o\u1EA1n" };
			model_ChiTietBangLuong = new DefaultTableModel(colHeader, 0) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] { false, false, false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};

			table.setModel(model_ChiTietBangLuong);
			model_ChiTietBangLuong.setRowCount(0);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.setColumnSelectionAllowed(false);
			columnModel.setColumnMargin(0);
			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(3).setResizable(false);

			JTableHeader tbBangLuong = table.getTableHeader();
			tbBangLuong.setBackground(new Color(151, 201, 219));
			tbBangLuong.setFont(new Font("Tahoma", Font.BOLD, 16));
			int rowHeight = 30;
			int rowMargin = 10;
			table.setRowHeight(rowHeight);
			table.setIntercellSpacing(new java.awt.Dimension(0, rowMargin));

			JButton btnXuat = new JButton("Xuất Excel");
			btnXuat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					exportExcel(table, bangLuongThoLamDan);
				}
			});
			btnXuat.setBounds(835, 640, 124, 40);
			contentPane.add(btnXuat);

			JLabel lblLuongSP = new JLabel("Lương sản phẩm : ");
			lblLuongSP.setForeground(Color.BLACK);
			lblLuongSP.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblLuongSP.setBounds(30, 499, 300, 20);
			contentPane.add(lblLuongSP);

			JLabel lblluongSP = new JLabel();
			lblluongSP.setForeground(Color.RED);
			lblluongSP.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblluongSP.setBounds(304, 499, 300, 20);
			contentPane.add(lblluongSP);
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			double luong = dao_LuongThoLamDan.layTongThuNhapTungThang(
					bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien(), bangLuongThoLamDan.getThang(),
					bangLuongThoLamDan.getNam());
			lblluongSP.setText(decimalFormat.format(luong));

			JLabel lblPCThamNien = new JLabel("Phụ cấp thâm niên : ");
			lblPCThamNien.setForeground(Color.BLACK);
			lblPCThamNien.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPCThamNien.setBounds(30, 539, 300, 20);
			contentPane.add(lblPCThamNien);

			JLabel lblPCTN = new JLabel();
			lblPCTN.setForeground(Color.RED);
			lblPCTN.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPCTN.setBounds(304, 539, 300, 20);
			contentPane.add(lblPCTN);
			lblPCTN.setText(decimalFormat.format(bangLuongThoLamDan.getThoLamDan()
					.tinhPhuCapThamNienThoLamDan(bangLuongThoLamDan.getThoLamDan().tinhHeSoLuong())));

			JLabel lblPCAnTrua = new JLabel("Phụ cấp ăn trưa : ");
			lblPCAnTrua.setForeground(Color.BLACK);
			lblPCAnTrua.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPCAnTrua.setBounds(30, 579, 300, 20);
			contentPane.add(lblPCAnTrua);

			JLabel lblPCAT = new JLabel();
			lblPCAT.setForeground(Color.RED);
			lblPCAT.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPCAT.setBounds(304, 579, 300, 20);
			contentPane.add(lblPCAT);
			lblPCAT.setText(decimalFormat.format(900000));

			JLabel lblTienBH = new JLabel();
			lblTienBH.setForeground(Color.RED);
			lblTienBH.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblTienBH.setBounds(304, 619, 300, 20);
			contentPane.add(lblTienBH);
			lblTienBH.setText(decimalFormat.format((3000000 * 0.08 + 3000000 * 0.015 + 3000000 * 0.01)));

			JLabel lblTienBaoHiem = new JLabel("Tiền đóng bảo hiểm : ");
			lblTienBaoHiem.setForeground(Color.BLACK);
			lblTienBaoHiem.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblTienBaoHiem.setBounds(30, 619, 300, 20);
			contentPane.add(lblTienBaoHiem);

			JLabel lblLuongThucLinh = new JLabel("Lương thực lĩnh : ");
			lblLuongThucLinh.setForeground(Color.BLACK);
			lblLuongThucLinh.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblLuongThucLinh.setBounds(30, 659, 300, 20);
			contentPane.add(lblLuongThucLinh);

			JLabel lblLuongTL = new JLabel();
			lblLuongTL.setForeground(Color.RED);
			lblLuongTL.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblLuongTL.setBounds(304, 659, 300, 20);
			contentPane.add(lblLuongTL);
			double luongThucLinh = bangLuongThoLamDan.tinhLuongThucLinh(luong);
			lblLuongTL.setText(decimalFormat.format(luongThucLinh));

			List<Object[]> list = dao_ChamCongThoLamDan.laySoLuongLamDuocCuaThang(
					bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien(), bangLuongThoLamDan.getThang(),
					bangLuongThoLamDan.getNam());
			List<Integer> soLuongSP = new ArrayList<>();
			for (Object[] x : list) {
				soLuongSP.add((Integer) x[3]);
			}

			List<Object[]> listCongDoans = dao_CongDoan.getCongDoanTheoTLDNgayThang(
					bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien(), bangLuongThoLamDan.getThang(),
					bangLuongThoLamDan.getNam());

			for (int i = 0; i < listCongDoans.size(); i++) {
				Object[] listCD = listCongDoans.get(i);
				Dan dan = dao_Dan.getDanTheoMaSanPham(listCD[1].toString());
				CongDoan congDoan = dao_CongDoan.getCongDoanTheoMaCongDoan(listCD[2].toString());
				Object[] objects = { dan.getTenSanPham(), congDoan.getTenCongDoan(), soLuongSP.get(i),
						congDoan.getGiaCongDoan() };
				model_ChiTietBangLuong.addRow(objects);
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addSalaryTableTitle(Sheet sheet, String title, int tableWidth) {
		org.apache.poi.ss.usermodel.Font titleFont = sheet.getWorkbook().createFont();
		titleFont.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short) 16);

		CellStyle titleCellStyle = sheet.getWorkbook().createCellStyle();
		titleCellStyle.setFont(titleFont);
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Row titleRow = sheet.createRow(0);

		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleCellStyle);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, tableWidth - 1));
	}

	public static boolean isFileOpen(File file) {
		if (!file.exists() || file.isDirectory()) {
			return false;
		}

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
				FileChannel channel = raf.getChannel();
				FileLock lock = channel.tryLock()) {
			if (lock == null) {
				return true;
			}
		} catch (IOException e) {
			return true;
		}
		return false;
	}

	public static void exportExcel(JTable table, BangLuongThoLamDan bangLuongThoLamDan) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu tệp Excel");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp Excel (.xls)", "xls");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				File fileToSave = fileChooser.getSelectedFile();
				String filePath = fileToSave.getAbsolutePath();
				if (fileToSave.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Tệp đã tồn tại. Bạn có muốn ghi đè không?",
							"Xác nhận ghi đè", JOptionPane.YES_NO_OPTION);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}
				}
				if (!filePath.endsWith(".xls")) {
					filePath += ".xls";
				}
				File file = new File(filePath);

				if (isFileOpen(file)) {
					JOptionPane.showMessageDialog(null, "File đang mở vui lòng đóng và thử lại");
				} else {

					Workbook workbook = new HSSFWorkbook();
					String tenSheet = "ChiTietBangLuongThoLamDan"
							+ bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien();
					Sheet sheet = workbook.createSheet(tenSheet);
					org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
					headerFont.setBoldweight((short) 12);
					headerFont.setFontHeightInPoints((short) 12);
					String title = "Phiếu Lương Tháng " + bangLuongThoLamDan.getThang() + " Năm "
							+ bangLuongThoLamDan.getNam();

					int tableWidth = 4;
					addSalaryTableTitle(sheet, title, tableWidth);

					CellStyle headerCellStyle = workbook.createCellStyle();
					headerCellStyle.setFont(headerFont);
					Row rowThongTin = sheet.createRow(2);
					Object[] rowTT = { "Mã TLD", bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien(), "Tên TLD",
							bangLuongThoLamDan.getThoLamDan().getHoTen() };
					for (int col = 0; col < rowTT.length; col++) {
						Object cellValue = rowTT[col];
						if (cellValue != null) {
							if (cellValue instanceof String) {
								rowThongTin.createCell(col).setCellValue((String) cellValue);
							} else if (cellValue instanceof Number) {
								rowThongTin.createCell(col).setCellValue(((Number) cellValue).doubleValue());
							} else {
								rowThongTin.createCell(col).setCellValue(cellValue.toString());
							}
						}
					}

					Row headerRow = sheet.createRow(4);
					for (int i = 0; i < table.getColumnCount(); i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(table.getColumnName(i));
						cell.setCellStyle(headerCellStyle);
					}

					CellStyle dateCellStyle = workbook.createCellStyle();
					CreationHelper createHelper = workbook.getCreationHelper();
					dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

					for (int col = 0; col < table.getColumnCount(); col++) {
						headerRow.createCell(col).setCellValue(table.getColumnName(col));
					}

					for (int row = 0; row < table.getRowCount(); row++) {
						Row dataRow = sheet.createRow(row + 5);
						for (int col = 0; col < table.getColumnCount(); col++) {
							Object cellValue = table.getValueAt(row, col);
							if (cellValue != null) {
								if (cellValue instanceof String) {
									dataRow.createCell(col).setCellValue((String) cellValue);
								} else if (cellValue instanceof Number) {
									dataRow.createCell(col).setCellValue(((Number) cellValue).doubleValue());
								} else {
									dataRow.createCell(col).setCellValue(cellValue.toString());
								}
							}
						}
					}

					int row = table.getRowCount();

					int salaryColumnIndex = 2;
					int totalRowNum = row + 6;
					Row totalRow = sheet.createRow(totalRowNum);
					Cell totalLabelCell = totalRow.createCell(0);
					totalLabelCell.setCellValue("Tổng số lượng sản phẩm:");

					Cell totalValueCell = totalRow.createCell(salaryColumnIndex);
					String cellRange = "C6:C" + (totalRowNum);
					totalValueCell.setCellFormula("SUM(" + cellRange + ")");

					Row Luong_Data_Row = sheet.createRow(row + 7);
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
					double luong = dao_LuongThoLamDan.layTongThuNhapTungThang(
							bangLuongThoLamDan.getThoLamDan().getMaCongNhanVien(), bangLuongThoLamDan.getThang(),
							bangLuongThoLamDan.getNam());
					Object[] luongData = { "Lương được nhận", "", "", luong };
					for (int col = 0; col < luongData.length; col++) {
						Object cellValue = luongData[col];
						if (cellValue != null) {
							Cell cell = Luong_Data_Row.createCell(col);
							if (cellValue instanceof String) {
								cell.setCellValue((String) cellValue);
							} else if (cellValue instanceof Number) {
								cell.setCellValue(((Number) cellValue).doubleValue());
								if (col == 3) {
									cell.setCellStyle(cellStyle);
								}
							} else {
								cell.setCellValue(cellValue.toString());
							}
						}
					}

					Row PCTN_Data_Row = sheet.createRow(row + 8);
					Object[] PCTNData = { "Phụ cấp thâm niên", "", "", bangLuongThoLamDan.getThoLamDan()
							.tinhPhuCapThamNienThoLamDan(bangLuongThoLamDan.getThoLamDan().tinhHeSoLuong()) };
					for (int col = 0; col < PCTNData.length; col++) {
						Object cellValue = PCTNData[col];
						if (cellValue != null) {
							Cell cell = PCTN_Data_Row.createCell(col);
							if (cellValue instanceof String) {
								cell.setCellValue((String) cellValue);
							} else if (cellValue instanceof Number) {
								cell.setCellValue(((Number) cellValue).doubleValue());
								if (col == 3) {
									cell.setCellStyle(cellStyle);
								}
							} else {
								cell.setCellValue(cellValue.toString());
							}
						}
					}

					Row PCAT_Data_Row = sheet.createRow(row + 9);
					Object[] PCATData = { "Phụ cấp ăn trưa", "", "", 900000 };
					for (int col = 0; col < PCATData.length; col++) {
						Object cellValue = PCATData[col];
						if (cellValue != null) {
							Cell cell = PCAT_Data_Row.createCell(col);
							if (cellValue instanceof String) {
								cell.setCellValue((String) cellValue);
							} else if (cellValue instanceof Number) {
								cell.setCellValue(((Number) cellValue).doubleValue());
								if (col == 3) {
									cell.setCellStyle(cellStyle);
								}
							} else {
								cell.setCellValue(cellValue.toString());
							}
						}
					}

					Row TienDongBaoHiem_Data_Row = sheet.createRow(row + 10);
					Object[] TienDongBaoHiem_Data = { "Tiền đóng bảo hiểm", "", "",
							3000000 * 0.08 + 3000000 * 0.015 + 3000000 * 0.01 };
					for (int col = 0; col < TienDongBaoHiem_Data.length; col++) {
						Object cellValue = TienDongBaoHiem_Data[col];
						if (cellValue != null) {
							Cell cell = TienDongBaoHiem_Data_Row.createCell(col);
							if (cellValue instanceof String) {
								cell.setCellValue((String) cellValue);
							} else if (cellValue instanceof Number) {
								cell.setCellValue(((Number) cellValue).doubleValue());
								if (col == 3) {
									cell.setCellStyle(cellStyle);
								}
							} else {
								cell.setCellValue(cellValue.toString());
							}
						}
					}

					int finalTotalRowNum = table.getRowCount() + 11;
					Row finalTotalRow = sheet.createRow(finalTotalRowNum);
					Cell finalTotalLabelCell = finalTotalRow.createCell(0);
					finalTotalLabelCell.setCellValue("Tổng cộng sau bảo hiểm:");

					Cell finalTotalValueCell = finalTotalRow.createCell(3);
					String luongCellRef = "D" + (finalTotalRowNum - 3);
					String pctnCellRef = "D" + (finalTotalRowNum - 2);
					String pcatCellRef = "D" + (finalTotalRowNum - 1);
					String bhiemCellRef = "D" + (finalTotalRowNum - 0);

					finalTotalValueCell
							.setCellFormula(luongCellRef + "+" + pctnCellRef + "+" + pcatCellRef + "-" + bhiemCellRef);
					finalTotalValueCell.setCellStyle(cellStyle);

					for (int i = 0; i < table.getColumnCount(); i++) {
						sheet.autoSizeColumn(i);
					}

					try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
						workbook.write(fileOut);
						JOptionPane.showMessageDialog(null, "Tạo và lưu tệp Excel thành công!");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lỗi khi tạo và lưu tệp Excel!");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
