import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Helper {

	static void drawDebug(Graphics g) {
		g.setColor(Color.black);
		int drawY = 0, incY = 15;
		g.drawString("FPS: " + AbsolutionGame.fps + ", TPS: " + AbsolutionGame.curTps, 15, drawY += incY);
	}

	static int displayError(String msg) {
		return JOptionPane.showOptionDialog(null, msg, "Error!", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
	}
}
