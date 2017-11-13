package Handler;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Helper {

	private int drawY, incY;
	private GameInfo gameInfo;
	private BuilderHandler builderHandler;

	public Helper(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		reset();
	}

	public void setBuilderHandler(BuilderHandler builderHandler) {
		this.builderHandler = builderHandler;
	}

	public void reset() {
		drawY = 0;
		incY = 15;
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.black);
		g.drawString("General Info:", 15, drawY += incY);
		g.drawString("FPS: " + gameInfo.getFPS() + ", TPS: " + gameInfo.getCurTPS(), 15, drawY += incY);
		g.drawString("MouseX: " + gameInfo.getMouseX() + ", MouseY: " + gameInfo.getMouseY(), 15, drawY += incY);
		g.drawString("Mouse Button: " + gameInfo.getMouseButton(), 15, drawY += incY);
		g.drawString("Mouse Dragging: " + gameInfo.getMouseDragging(), 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public void drawBuilderDebug(Graphics g) {
		g.setColor(Color.black);
		g.drawString("World Builder Info:", 15, drawY += incY);
		g.drawString("Selected ID: " + builderHandler.selectedID, 15, drawY += incY);
		g.drawString("Grid Locked: " + builderHandler.gridLock, 15, drawY += incY);
		if (builderHandler.gridLock)
			g.drawString("X Locked Pos: " + (gameInfo.getMouseX() / 16) * 16 + ", Y Locked Pos: "
					+ (gameInfo.getMouseY() / 16) * 16, 15, drawY += incY);
		g.drawString("Selected Rotation: " + builderHandler.rotateDegs, 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public void drawBuilderGrid(Graphics g) {
		if (builderHandler.gridLock) {
			g.setColor(new Color(0xFFD1D1));
			for (int i = 0; i < GameInfo.gameWidth; i += 16)
				g.drawLine(i, 0, i, GameInfo.height);
			for (int i = 0; i < GameInfo.height; i += 16)
				g.drawLine(0, i, GameInfo.gameWidth, i);
		}
	}

	public static int displayError(String msg) {
		return JOptionPane.showOptionDialog(null, msg, "Error!", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
	}
}
