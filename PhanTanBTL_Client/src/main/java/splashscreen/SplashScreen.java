package splashscreen;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class SplashScreen extends javax.swing.JDialog {

	private static final long serialVersionUID = -1080501022907001710L;

	public SplashScreen(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		getContentPane().setBackground(Color.decode("#B8E1FF"));
		getContentPane().setBackground(new Color(221, 221, 221));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	private void initComponents() {

		curvesPanel1 = new splashscreen.CurvesPanel();
		jLabel1 = new javax.swing.JLabel();
		pro = new splashscreen.ProgressBarCustom();
		lbStatus = new javax.swing.JLabel();

		pro.setForeground(Color.decode("#011F82"));
		pro.setBackground(Color.decode("#5586B0"));
		pro.setColorString(Color.decode("#CED1E6"));

		lbStatus.setForeground(Color.decode("#6D92D0"));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/logo2.png")));

		lbStatus.setForeground(new java.awt.Color(200, 200, 200));
		lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbStatus.setText("Status");

		javax.swing.GroupLayout curvesPanel1Layout = new javax.swing.GroupLayout(curvesPanel1);
		curvesPanel1.setLayout(curvesPanel1Layout);
		curvesPanel1Layout
				.setHorizontalGroup(curvesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(curvesPanel1Layout.createSequentialGroup().addContainerGap(277, Short.MAX_VALUE)
								.addGroup(curvesPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(pro, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(278, Short.MAX_VALUE)));
		curvesPanel1Layout
				.setVerticalGroup(curvesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(curvesPanel1Layout.createSequentialGroup().addContainerGap(93, Short.MAX_VALUE)
								.addComponent(jLabel1).addGap(18, 18, 18)
								.addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 5,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lbStatus).addContainerGap(92, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(3, 3, 3).addComponent(curvesPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(3, 3, 3)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(3, 3, 3).addComponent(curvesPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(3, 3, 3)));

		pack();
		setLocationRelativeTo(null);
	}

	private void formWindowOpened(java.awt.event.WindowEvent evt) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					doTask("Connect To Database ...", 10);
					doTask("10% ...", 20);
					doTask("20% ...", 30);
					doTask("30% ...", 40);
					doTask("40% ...", 50);
					doTask("50% ...", 60);
					doTask("60% ...", 70);
					doTask("70% ...", 80);
					doTask("80% ...", 90);
					doTask("90% ...", 100);
					doTask("Done ...", 100);
					dispose();
					curvesPanel1.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void doTask(String taskName, int progress) throws Exception {
		lbStatus.setText(taskName);
		Thread.sleep(500);
		pro.setValue(progress);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatMacLightLaf());
				} catch (Exception ex) {
					System.err.println("Failed to initialize LaF");
				}
				try {
					SplashScreen dialog = new SplashScreen(new javax.swing.JFrame(), true);
					dialog.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent e) {
							System.exit(0);
						}
					});
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private splashscreen.CurvesPanel curvesPanel1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel lbStatus;
	private splashscreen.ProgressBarCustom pro;
}
