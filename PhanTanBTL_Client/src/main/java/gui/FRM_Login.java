package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import entity.TaiKhoan;
import idao.I_DAO_TaiKhoan;
import splashscreen.SplashScreen;

public class FRM_Login extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String URL = "rmi://DESKTOP-UH6CFJP:5511/";

	private JPanel contentPane;
	private JTextField txtTaiKhoan;
	private JPasswordField txtpwd;
	private int countSaiMatKhau = 0;
	private JButton btn_login, btn_exit;
	private I_DAO_TaiKhoan dao_taiKhoan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatMacLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
			ex.printStackTrace();
			return;

		}

		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(new FlatMacLightLaf());
			} catch (Exception ex) {
				System.err.println("Failed to initialize LaF");
				ex.printStackTrace();
			}
		});

		final FRM_Login[] frame = new FRM_Login[1];

		Thread splashThread = new Thread(() -> {
			SplashScreen splash = new splashscreen.SplashScreen(null, true);
			splash.setVisible(true);
			synchronized (splash) {
				while (splash.isVisible()) {
					try {
						splash.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			EventQueue.invokeLater(() -> {
				frame[0].setVisible(true);
			});
		});

		Thread loginInitThread = new Thread(() -> {
			frame[0] = new FRM_Login();
		});

		splashThread.start();
		loginInitThread.start();
	}

	/**
	 * Create the frame.
	 */
	public FRM_Login() {
		try {
			dao_taiKhoan = (I_DAO_TaiKhoan) Naming.lookup(URL + "I_DAO_TaiKhoan");
			setTitle("Form login");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1450, 700);
			setIconImage(Toolkit.getDefaultToolkit().getImage(FRM_GiaoDienChinh.class.getResource("/icons/Icon.png")));
			setLocationRelativeTo(null);
			contentPane = new JPanel();
			contentPane.setForeground(Color.decode("#011F82"));
			contentPane.setBackground(Color.decode("#B8E1FF"));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			contentPane.setAlignmentX(BOTTOM_ALIGNMENT);

			JLabel lblNewLabel = new JLabel("PHẦN MỀM QUẢN LÝ LƯƠNG");
			lblNewLabel.setForeground(new Color(0, 27, 72));
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
			lblNewLabel.setBackground(new Color(218, 205, 212));
			lblNewLabel.setBounds(324, 20, 788, 97);
			contentPane.add(lblNewLabel);

			JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(131, 171, 185, -83);
			contentPane.add(desktopPane);

			JLabel jbicon = new JLabel();
			jbicon.setIcon(new ImageIcon(FRM_Login.class.getResource("/icons/form_login.jpg")));
			jbicon.setBounds(165, 156, 563, 431);
			contentPane.add(jbicon);

			JPanel login = new JPanel();
			login.setBackground(new Color(221, 242, 251));
			login.setForeground(new Color(221, 242, 251));
			login.setBorder(new LineBorder(new Color(221, 242, 251), 10, true));
			login.setBounds(785, 171, 540, 400);
			contentPane.add(login);
			login.setLayout(null);

			JLabel login_title = new JLabel("ĐĂNG NHẬP");
			login_title.setFont(new Font("Tahoma", Font.PLAIN, 34));
			login_title.setBounds(176, 35, 188, 37);
			login.add(login_title);

			JLabel tk_login = new JLabel("Tài khoản");
			tk_login.setForeground(new Color(0, 27, 72));
			tk_login.setFont(new Font("Tahoma", Font.PLAIN, 22));
			tk_login.setBounds(70, 81, 100, 32);
			login.add(tk_login);

			JLabel mk_login = new JLabel("Mật khẩu");
			mk_login.setForeground(new Color(0, 27, 72));
			mk_login.setFont(new Font("Tahoma", Font.PLAIN, 22));
			mk_login.setBounds(70, 181, 100, 32);
			login.add(mk_login);

			txtTaiKhoan = new JTextField();
			txtTaiKhoan.setFont(new Font("Tahoma", Font.PLAIN, 22));
			txtTaiKhoan.setBounds(70, 114, 400, 36);
			login.add(txtTaiKhoan);
			txtTaiKhoan.setColumns(10);
			txtTaiKhoan.setText("NV002");
			txtTaiKhoan.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btn_login.doClick();
					}
				}
			});

			JCheckBox checkbox_show = new JCheckBox("Hiển thị mật khẩu");
			checkbox_show.setFont(new Font("Tahoma", Font.PLAIN, 14));
			checkbox_show.setBackground(new Color(221, 242, 251));
			checkbox_show.setBounds(70, 268, 139, 22);
			login.add(checkbox_show);

			txtpwd = new JPasswordField();
			txtpwd.setFont(new Font("Tahoma", Font.PLAIN, 22));
			txtpwd.setBounds(70, 214, 400, 36);
			txtpwd.setText("123");
			txtpwd.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btn_login.doClick();
					}
				}
			});
			login.add(txtpwd);
			Font defaultFont = txtpwd.getFont();

			checkbox_show.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkbox_show.isSelected()) {
						txtpwd.setEchoChar((char) 0);
					} else {
						txtpwd.setEchoChar('●');
					}
					txtpwd.setFont(new Font("Tahoma", Font.PLAIN, defaultFont.getSize()));

				}
			});
			btn_login = new JButton("Đăng nhập");
			btn_login.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btn_login.setBackground(new Color(255, 255, 255));
			btn_login.setBounds(73, 319, 120, 40);
			login.add(btn_login);

			btn_exit = new JButton("Thoát");
			btn_exit.setBackground(new Color(255, 255, 255));
			btn_exit.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btn_exit.setBounds(350, 321, 120, 40);
			login.add(btn_exit);
			btn_login.addActionListener(this);
			btn_exit.addActionListener(this);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void login() throws RemoteException {
		if (countSaiMatKhau > 2) {
			JOptionPane.showMessageDialog(null, "Bạn đã nhập sai tài khoản quá 3 lần. Chương trình sẽ thoát!");
			System.exit(0);
		} else {
			String maTaiKhoan = txtTaiKhoan.getText().trim();
			@SuppressWarnings("deprecation")
			String matKhau = txtpwd.getText().toString().trim();
			TaiKhoan taiKhoan = dao_taiKhoan.getTaiKhoanTheoMaTaiKhoan(maTaiKhoan);
			if (taiKhoan.getTaiKhoan() == null) {
				JOptionPane.showMessageDialog(null, "Tài khoản không đúng!");
				countSaiMatKhau++;
			} else if (!taiKhoan.getMatKhau().equals(matKhau)) {
				JOptionPane.showMessageDialog(null, "Mật khẩu không đúng!");
				countSaiMatKhau++;
			} else {
				String maPB = taiKhoan.getNhanVien().getPhongBan().getMaPhongBan();
				if (maPB.equals("PB005")) {
					FRM_GiaoDienChinhQLSanXuat qlsx = new FRM_GiaoDienChinhQLSanXuat();
					qlsx.setVisible(true);
					setVisible(false);

				} else {
					FRM_GiaoDienChinhQLNhanSu qlns = new FRM_GiaoDienChinhQLNhanSu();
					qlns.setVisible(true);
					setVisible(false);

				}
				this.setVisible(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btn_login)) {
			try {
				login();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btn_exit)) {
			System.out.println("Thoát");
			int option = JOptionPane.showConfirmDialog(null, "Bạn có thực sự muốn thoát?", "Thoát?",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}
}
