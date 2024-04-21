package splashscreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBarCustom extends JProgressBar {

	private static final long serialVersionUID = -7010023455025041878L;

	public Color getColorString() {
		return colorString;
	}

	public void setColorString(Color colorString) {
		this.colorString = colorString;
	}

	private Color colorString = new Color(0, 0, 0);

	public ProgressBarCustom() {
		setPreferredSize(new Dimension(100, 5));
		setBackground(new Color(0, 0, 0));
		setForeground(Color.decode("#5586B0"));
		setColorString(Color.decode("#CED1E6"));
		setUI(new BasicProgressBarUI() {

			@Override
			protected void paintString(Graphics grphcs, int i, int i1, int i2, int i3, int i4, Insets insets) {
				grphcs.setColor(getColorString());
				super.paintString(grphcs, i, i1, i2, i3, i4, insets);
			}
		});
	}
}
