import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Helper {
	private int drawY, incY;
	AbsolutionGame gameInfo;

	public Helper(AbsolutionGame gameInfo) {
		this.gameInfo = gameInfo;
		reset();
	}

	public void reset() {
		drawY = 0;
		incY = 15;
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.black);
		g.drawString("General Info:", 15, drawY += incY);
		g.drawString("FPS: " + gameInfo.fps + ", TPS: " + gameInfo.curTps, 15, drawY += incY);
		g.drawString("MouseX: " + gameInfo.mouseX + ", MouseY: " + gameInfo.mouseY, 15, drawY += incY);
		g.drawString("Mouse Button: " + gameInfo.mouseButton, 15, drawY += incY);
		g.drawString("Mouse Dragging: " + gameInfo.mouseDragging, 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public void drawBuilderDebug(Graphics g) {
		g.setColor(Color.black);
		g.drawString("World Builder Info:", 15, drawY += incY);
		g.drawString("Selected ID: " + gameInfo.selectedID, 15, drawY += incY);
		g.drawString("Grid Locked: " + gameInfo.gridLock, 15, drawY += incY);
		if (gameInfo.gridLock)
			g.drawString("X Locked Pos: " + (gameInfo.mouseX / 16) * 16 + ", Y Locked Pos: " + (gameInfo.mouseY / 16) * 16 , 15, drawY += incY);
		g.drawString("Selected Rotation: " + gameInfo.rotateDegs, 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public void drawBuilderGrid(Graphics g) {
		if (gameInfo.gridLock) {
			g.setColor(new Color(0xFFD1D1));
			for (int i = 0; i < gameInfo.gameWidth; i += 16)
				g.drawLine(i, 0, i, gameInfo.height);
			for (int i = 0; i < gameInfo.height; i += 16)
				g.drawLine(0, i, gameInfo.gameWidth, i);
		}
	}
	
	public void setBuilderRotateDegs(int rotate) {
		gameInfo.rotateDegs = rotate;
		gameInfo.rotateSelect.setText(rotate + "");
	}

	public static int displayError(String msg) {
		return JOptionPane.showOptionDialog(null, msg, "Error!", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
	}
}
